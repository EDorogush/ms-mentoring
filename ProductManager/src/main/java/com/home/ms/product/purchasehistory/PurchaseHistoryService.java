package com.home.ms.product.purchasehistory;

import com.home.ms.product.IdGenerator;
import org.springframework.stereotype.Service;

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
    return mapper.fromEntity(entity);
  }

  public void deleteGameFromHistory(String userId, String gameId) {
    repository.deleteByUserIdAndGameId(userId, gameId);
  }
}
