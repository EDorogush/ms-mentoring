package com.home.ms.product.purchasehistory;

import org.springframework.stereotype.Component;

@Component
public class PurchaseHistoryMapper {

  public PurchaseHistoryItem fromEntity(PurchaseHistoryEntity entity) {
    PurchaseHistoryItem item = new PurchaseHistoryItem();
    item.setId(entity.getId());
    item.setGameId(entity.getGameId());
    item.setDate(entity.getDate());
    return item;
  }

  public PurchaseHistoryEntity toEntity(PurchaseHistoryItemToPost item) {
    PurchaseHistoryEntity entity = new PurchaseHistoryEntity();
    entity.setGameId(item.getGameId());
    entity.setCost(item.getCost());
    entity.setDate(item.getDate());
    entity.setUserId(item.getUserId());
    return entity;
  }
}
