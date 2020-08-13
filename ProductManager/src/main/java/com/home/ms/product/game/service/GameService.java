package com.home.ms.product.game.service;

import com.home.ms.product.game.repository.GameEntity;
import com.home.ms.product.game.repository.GameRepository;
import com.home.ms.product.game.web.Game;
import com.home.ms.product.game.web.GameSearchFilter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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
        Game game = gameToPublish;
        game.setId(UUID.randomUUID().toString());
        return game;
    }

    public List<Game> searchGamesInUserScope(String userId, GameSearchFilter filter) {
        List<String> gameIds = getUserGames(userId);
        List<GameEntity> entityList = repository.findAllById(gameIds);
        return entityList.stream().map(mapper::formEntity).collect(Collectors.toList());
    }

    public List<Game> searchGamesInGeneralScope(GameSearchFilter filter) {
        List<GameEntity> entityList = repository.findAll();
        return entityList.stream().map(mapper::formEntity).collect(Collectors.toList());
    }


    public Game searchGameById(String id) {
        return new Game();
    }


    private List<String> getUserGames(String userId) {
        //request to purchaseHistoryManager to get user's games ids;
        return Arrays.asList("game-1", "game-2");

    }
}
