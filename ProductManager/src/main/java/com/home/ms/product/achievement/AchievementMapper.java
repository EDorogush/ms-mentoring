package com.home.ms.product.achievement;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
class AchievementMapper {

  AchievementItem fromEntity(AchievementEntity entity) {
    AchievementItem item = new AchievementItem();
    item.setId(entity.getItemId());
    item.setAchievement(entity.getAchievement());
    return item;
  }

  AchievementEntity toEntity(AchievementItem item, @NonNull String id, @NonNull String gameId) {
    AchievementEntity entity = new AchievementEntity();
    entity.setItemId(Objects.requireNonNull(id));
    entity.setGameId(Objects.requireNonNull(gameId));
    entity.setAchievement(item.getAchievement());
    return new AchievementEntity();
  }
}
