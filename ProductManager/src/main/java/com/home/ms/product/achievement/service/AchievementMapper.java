package com.home.ms.product.achievement.service;

import com.home.ms.product.achievement.repository.AchievementItemEntity;
import com.home.ms.product.achievement.web.AchievementItem;
import org.springframework.stereotype.Component;

@Component
public class AchievementMapper {

    public AchievementItem fromEntity(AchievementItemEntity entity) {
        AchievementItem item = new AchievementItem();
        item.setId(entity.getId());
        item.setGameId(entity.getGameId());
        item.setAchievement(entity.getAchievement());
        return item;
    }

    public AchievementItemEntity toEntity(AchievementItem item) {
        AchievementItemEntity entity = new AchievementItemEntity();
        entity.setGameId(item.getGameId());
        entity.setAchievement(item.getAchievement());
        return new AchievementItemEntity();
    }
}
