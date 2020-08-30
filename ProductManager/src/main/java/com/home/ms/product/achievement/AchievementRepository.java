package com.home.ms.product.achievement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** repository represents game's achievements catalog */
interface AchievementRepository extends JpaRepository<AchievementEntity, String> {

  List<AchievementEntity> findAllByGameId(String gameId);
}
