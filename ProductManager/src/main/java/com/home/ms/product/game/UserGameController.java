package com.home.ms.product.game;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games/{gameId}")
public class UserGameController {
  private final UserGamesService service;

  public UserGameController(UserGamesService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addGameForUser(@PathVariable String gameId, @RequestBody UserGameItem item) {
    service.addGame(item.getRecordId(), item.getUserId(), item.getGameId());
  }

//  @DeleteMapping
//  public void takeAwayAchievement(
//      @PathVariable String gameId,
//      @RequestParam @NonNull String ownedBy,
//      @RequestParam @NonNull String recordId) {
//    // todo:test nullable
//    service.deleteGame(recordId);
//  }
}
