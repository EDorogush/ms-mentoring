package com.home.ms.product.purchasehistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.ms.product.IdGenerator;
import com.home.ms.product.RequestFailedException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseHistoryService {
  private final PurchaseHistoryRepository repository;
  private final PurchaseHistoryMapper mapper;
  private final IdGenerator idGenerator;

  public PurchaseHistoryService(
      PurchaseHistoryRepository repository, PurchaseHistoryMapper mapper, IdGenerator generator) {
    this.repository = repository;
    this.mapper = mapper;
    this.idGenerator = generator;
  }

  public List<PurchaseHistoryItem> getUserItems(String userId) {
    List<PurchaseHistoryEntity> entity = repository.findByUserId(userId);
    return entity.stream().map(mapper::fromEntity).collect(Collectors.toList());
  }

  public List<String> getUserGameIds(String userId) {
    List<PurchaseHistoryEntity> entity = repository.findByUserId(userId);
    return entity.stream().map(PurchaseHistoryEntity::getGameId).collect(Collectors.toList());
  }

  public PurchaseHistoryItem addItem(PurchaseHistoryItem item, String userId) {
    PurchaseHistoryEntity entity = mapper.toEntity(item);
    entity.setUserId(userId);
    entity.setId(idGenerator.generateRandomUUID());
    repository.save(entity);
    try {
      sentData(entity);
    } catch (RequestFailedException e) {
      repository.deleteById(entity.getId());
      throw e;
    }
    return mapper.fromEntity(entity);
  }

  public void deleteGameFromHistory(String userId, String gameId) {
    repository.deleteByUserIdAndGameId(userId, gameId);
  }

  private void sentData(PurchaseHistoryEntity entity) {
    HttpClient client = HttpClient.newHttpClient();
    URI uri = URI.create("http://localhost:8080/games/" + entity.getGameId());
    PostUserHistoryRequestOperation requestOperation =
        new PostUserHistoryRequestOperation(client, uri, new ObjectMapper());
    requestOperation.doOperation(entity.getId(), entity.getUserId(), entity.getGameId());
  }
}
