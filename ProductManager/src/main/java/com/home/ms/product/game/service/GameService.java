package com.home.ms.product.game.service;

import com.home.ms.product.game.repository.GameEntity;
import com.home.ms.product.game.repository.GameRepository;
import com.home.ms.product.game.web.Game;
import com.home.ms.product.game.web.GameSearchFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameService {
    private final GameRepository repository;
    private final GameMapper mapper;
    private final IdGenerator idGenerator;

    public GameService(GameRepository repository, GameMapper mapper, IdGenerator idGenerator) {
        this.repository = repository;
        this.mapper = mapper;
        this.idGenerator = idGenerator;
    }

    public Game publishGame(Game gameToPublish) {
        GameEntity entity = mapper.toEntity(gameToPublish);
        entity.setId(idGenerator.generateRandomUUID());
        repository.save(entity);
        return mapper.fromEntity(entity);
    }

    public List<Game> searchGamesInUserScope(String userId, GameSearchFilter filter) {
        List<String> gameIds = getUserGames(userId);
        List<GameEntity> entityList = repository.findAllById(gameIds);
        return entityList.stream().map(mapper::fromEntity).collect(Collectors.toList());
    }

    public List<Game> searchGamesInGeneralScope(GameSearchFilter filter) {
        List<GameEntity> entityList = repository.findAll();
        return entityList.stream().map(mapper::fromEntity).collect(Collectors.toList());
    }

    public Game searchGameById(String id) {
        GameEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("game", id));
        return mapper.fromEntity(entity);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }


    private List<String> getUserGames(String userId) {
        //request to purchaseHistoryManager to get user's games ids;
        //purchase-history/games
        return Arrays.asList("game-1", "game-2");

    }
}
