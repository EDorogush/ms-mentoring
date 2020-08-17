package com.home.ms.product.game.service;

import com.home.ms.product.game.repository.GameEntity;
import com.home.ms.product.game.web.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public Game fromEntity(GameEntity entity) {
        Game game = new Game();
        game.setId(entity.getId());
        game.setTitle(entity.getTitle());
        game.setBasePrice(entity.getBasePrice());
        return game;
    }

    public GameEntity toEntity(Game game) {
        GameEntity entity = new GameEntity();
        entity.setId(game.getId());
        entity.setTitle(game.getTitle());
        entity.setBasePrice(game.getBasePrice());
        return entity;
    }
}
