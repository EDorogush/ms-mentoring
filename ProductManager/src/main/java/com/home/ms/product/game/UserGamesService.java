package com.home.ms.product.game;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class UserGamesService {
  private final UserGamesRepository repository;

  public UserGamesService(UserGamesRepository repository) {
    this.repository = repository;
  }

  List<String> searchUserGames(String userId) {
    return repository.findAllByUserId(userId).stream()
        .map(UserGamesEntity::getGameId)
        .collect(Collectors.toList());
  }
}
