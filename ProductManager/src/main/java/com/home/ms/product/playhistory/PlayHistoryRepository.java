package com.home.ms.product.playhistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayHistoryRepository extends JpaRepository<PlayHistoryEntity, String> {

  Optional<PlayHistoryEntity> findByUserIdAndGameId(String userId, String gameId);

  void deleteByUserIdAndGameId(String userId, String gameId);
}
