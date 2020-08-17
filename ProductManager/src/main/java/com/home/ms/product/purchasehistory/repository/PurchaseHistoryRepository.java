package com.home.ms.product.purchasehistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository {

    public List<PurchaseHistoryEntity> findByUserId(String userId);

    public void deleteByUserIdAndGameId(String userId, String gameId);

}
