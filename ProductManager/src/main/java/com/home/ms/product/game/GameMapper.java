package com.home.ms.product.game;

import org.springframework.stereotype.Component;

@Component
public class GameMapper {

  public GameItem fromEntity(GameEntity entity) {
    GameItem game = new GameItem();
    game.setId(entity.getId());
    game.setTitle(entity.getTitle());
    game.setBasePrice(entity.getBasePrice());
    return game;
  }

  public GameEntity toEntity(GameItem game) {
    GameEntity entity = new GameEntity();
    entity.setId(game.getId());
    entity.setTitle(game.getTitle());
    entity.setBasePrice(game.getBasePrice());
    return entity;
  }
}
