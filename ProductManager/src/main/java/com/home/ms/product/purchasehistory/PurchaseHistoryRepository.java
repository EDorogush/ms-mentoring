package com.home.ms.product.purchasehistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistoryEntity, String> {

  List<PurchaseHistoryEntity> findByUserId(String userId);

  void deleteByUserIdAndGameId(String userId, String gameId);
}
