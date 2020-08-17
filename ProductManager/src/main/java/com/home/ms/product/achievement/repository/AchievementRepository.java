package com.home.ms.product.achievement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<AchievementItemEntity, String> {

    public List<AchievementItemEntity> findAllByUserGameId(long userGameId);

}
