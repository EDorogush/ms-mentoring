package com.home.ms.product.game;

import com.home.ms.product.IdGenerator;
import com.home.ms.product.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {
  private final UserGamesService userGamesService;
  private final GameRepository repository;
  private final GameMapper mapper;
  private final IdGenerator idGenerator;

  public GameService(
      UserGamesService userGamesService,
      GameRepository repository,
      GameMapper mapper,
      IdGenerator idGenerator) {
    this.userGamesService = userGamesService;
    this.repository = repository;
    this.mapper = mapper;
    this.idGenerator = idGenerator;
  }

  public GameItem publishGame(GameItem gameToPublish) {
    GameEntity entity = mapper.toEntity(gameToPublish);
    entity.setId(idGenerator.generateRandomUUID());
    repository.save(entity);
    return mapper.fromEntity(entity);
  }

  public List<GameItem> searchGamesInUserScope(String userId, GameSearchFilter filter) {
    List<String> gameIds = getUserGames(userId);
    List<GameEntity> entityList = repository.findAllById(gameIds);
    return entityList.stream().map(mapper::fromEntity).collect(Collectors.toList());
  }

  public List<GameItem> searchGamesInGeneralScope(GameSearchFilter filter) {
    List<GameEntity> entityList = repository.findAll();
    return entityList.stream().map(mapper::fromEntity).collect(Collectors.toList());
  }

  public GameItem searchGameById(String id) {
    GameEntity entity =
        repository.findById(id).orElseThrow(() -> new NotFoundException("game", id));
    return mapper.fromEntity(entity);
  }

  public void deleteById(String id) {
    repository.deleteById(id);
  }

  private List<String> getUserGames(String userId) {
    // request to purchaseHistoryManager to get user's games ids;
    // purchase-history/games
    return userGamesService.searchUserGames(userId);
    // return Arrays.asList("game-1", "game-2");
  }
}
