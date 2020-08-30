package com.home.ms.product.playhistory;

import org.springframework.stereotype.Component;

@Component
public class PlayHistoryMapper {

  PlayHistoryItem fromEntity(PlayHistoryEntity entity) {
    PlayHistoryItem item = new PlayHistoryItem();
    item.setId(entity.getId());
    item.setPlayedHours(entity.getTime());
    return item;
  }

  PlayHistoryEntity toEntity(PlayHistoryItem item) {
    PlayHistoryEntity entity = new PlayHistoryEntity();
    entity.setId(item.getId());
    entity.setTime(item.getPlayedHours());
    return entity;
  }
}
