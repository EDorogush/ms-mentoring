package com.home.ms.product.game;

import com.home.ms.product.ConflictOperationException;
import com.home.ms.product.IdGenerator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
class UserGamesService {
  private final UserGamesRepository repository;
  // todo: consider spring's interface
  private final IdGenerator idGenerator;

  public UserGamesService(UserGamesRepository repository, IdGenerator idGenerator) {
    this.repository = repository;
    this.idGenerator = idGenerator;
  }

  List<String> searchUserGames(String userId) {
    return repository.findAllByUserId(userId).stream()
        .map(UserGamesEntity::getGameId)
        .collect(Collectors.toList());
  }

  void addGame(String recordId, String userId, String gameId) {
    UserGamesEntity entity = new UserGamesEntity();
    entity.setGameId(gameId);
    entity.setUserId(userId);
    entity.setId(recordId);
    try {
      repository.save(entity);
    } catch (DataIntegrityViolationException e) {
      throw new ConflictOperationException(
          MessageFormat.format(
              "game with id = {0} is already in list for user with id = {1}", gameId, userId));
    }
  }

  void deleteGame(String recordId) {
    try {
      repository.deleteById(recordId);
    } catch (EmptyResultDataAccessException e) {
      // do nothing
    }
  }
}
