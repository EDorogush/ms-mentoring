package com.home.ms.product.playhistory;

import com.home.ms.product.IdGenerator;
import com.home.ms.product.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayHistoryService {
  private final PlayHistoryRepository repository;
  private final PlayHistoryMapper mapper;
  private final IdGenerator generator;

  public PlayHistoryService(
      PlayHistoryRepository repository, PlayHistoryMapper mapper, IdGenerator generator) {
    this.repository = repository;
    this.mapper = mapper;
    this.generator = generator;
  }

  public PlayHistoryItem search(String userId, String gameId) {
    return repository
        .findByUserIdAndGameId(userId, gameId)
        .map(mapper::fromEntity)
        .orElseThrow(() -> new NotFoundException("playHistory not found"));
  }

  public PlayHistoryItem save(PlayHistoryItem item, String userId, String gameId) {
    PlayHistoryEntity entity = mapper.toEntity(item);
    entity.setId(generator.generateRandomUUID());
    entity.setGameId(gameId);
    entity.setUserId(userId);
    PlayHistoryEntity entityFromDb = repository.save(entity);
    return mapper.fromEntity(entityFromDb);
  }

  public void delete(String userId, String gameId) {
    repository.deleteByUserIdAndGameId(userId, gameId);
  }

  public void updatePlayedHours(PlayHistoryItem delta, String userId, String gameId) {
    PlayHistoryEntity entity =
        repository
            .findByUserIdAndGameId(userId, gameId)
            .orElseThrow(() -> new NotFoundException("playHistory not found"));
    entity.setTime(entity.getTime() + delta.getPlayedHours());
    repository.save(entity);
  }
  // todo: need to think about using one id field instead of two. or add unique index for two fields
  // (userId, gameId)
  // todo: need to refactor update method

}
