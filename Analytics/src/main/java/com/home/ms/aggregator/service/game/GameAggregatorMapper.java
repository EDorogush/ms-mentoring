package com.home.ms.aggregator.service.game;

import com.home.ms.aggregator.repository.GameAggregatorEntity;
import com.home.ms.aggregator.web.game.GameAggregator;

public class GameAggregatorMapper {
    public GameAggregatorEntity toEntity(GameAggregator item) {
        GameAggregatorEntity entity = new GameAggregatorEntity();
        entity.setGameId(item.getGameId());
        entity.setAchvmtValue(item.getAchievementNumber());
        entity.setPlayerValue(item.getUserNumber());
        return entity;
    }

    public GameAggregator fromEntity(GameAggregatorEntity entity) {
        GameAggregator item = new GameAggregator();
        item.setGameId(entity.getGameId());
        item.setUserNumber(entity.getPlayerValue());
        item.setAchievementNumber(entity.getAchvmtValue());
        return item;
    }
}
