package com.home.ms.product.achievement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface UserAchievementRepository extends JpaRepository<UserAchievementEntity, String> {
  List<UserAchievementEntity> findAllByUserId(String userId);

  List<UserAchievementEntity> findAllByGameId(String gameId);

  List<UserAchievementEntity> findAllByUserIdAndGameId(String userId, String gameId);

  void deleteByUserIdAndGameId(String userId, String gameId);
}
