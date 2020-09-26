package com.home.ms.product.purchasehistory;

import com.home.ms.product.IdGenerator;
import com.home.ms.product.RequestFailedException;
import com.home.ms.product.purchasehistory.usergamehistory.UserGameHistoryRequestProducer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseHistoryService {
  private final PurchaseHistoryRepository repository;
  private final PurchaseHistoryMapper mapper;
  private final IdGenerator idGenerator;
  private final UserGameHistoryRequestProducer requestProducer;

  public PurchaseHistoryService(
      PurchaseHistoryRepository repository,
      PurchaseHistoryMapper mapper,
      IdGenerator generator,
      UserGameHistoryRequestProducer requestProducer) {
    this.repository = repository;
    this.mapper = mapper;
    this.idGenerator = generator;
    this.requestProducer = requestProducer;
  }

  public List<PurchaseHistoryItem> getUserItems(String userId) {
    List<PurchaseHistoryEntity> entity = repository.findByUserId(userId);
    return entity.stream().map(mapper::fromEntity).collect(Collectors.toList());
  }

  public List<String> getUserGameIds(String userId) {
    List<PurchaseHistoryEntity> entity = repository.findByUserId(userId);
    return entity.stream().map(PurchaseHistoryEntity::getGameId).collect(Collectors.toList());
  }

  public PurchaseHistoryItem addItem(PurchaseHistoryItemToPost item) {
    PurchaseHistoryEntity entity = mapper.toEntity(item);
    entity.setId(idGenerator.generateRandomUUID());
    repository.save(entity);
    try {
      requestProducer.sendPostOne(entity.getId(), entity.getUserId(), entity.getGameId());
    } catch (RequestFailedException e) {
      repository.deleteById(entity.getId());
      throw e;
    }
    return mapper.fromEntity(entity);
  }

  public void deleteGameFromHistory(String userId, String gameId) {
    repository.deleteByUserIdAndGameId(userId, gameId);
  }
}
