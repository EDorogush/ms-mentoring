package com.home.ms.product.game;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<GameItem> publishGame(@RequestBody GameItem gameToPublish) {
    GameItem game = gameService.publishGame(gameToPublish);
    return ResponseEntity.created(buildLocation(game.getId())).body(game);
  }

  @GetMapping
  public List<GameItem> searchGames(GameGetRequestParameters parameters) {
    GameSearchFilter filter = new GameSearchFilter();
    final String userId = getUserId(parameters.getOwnedBy());
    if (userId == null) {
      return gameService.searchGamesInGeneralScope(filter);
    }
    return gameService.searchGamesInUserScope(userId, filter);
  }

  @GetMapping("/{gameId}")
  public GameItem searchGame(@PathVariable("gameId") String id) {
    return gameService.searchGameById(id);
  }

  @DeleteMapping("/{gameId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteGame(@PathVariable("gameId") String id) {
    gameService.deleteById(id);
  }

  private URI buildLocation(String id) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
  }

  private String getUserId(String ownedBy) {
    if (ownedBy == null || !ownedBy.equals("me")) {
      return ownedBy;
    }
    return "user_id-9";
  }

  private GameSearchFilter getFilterFromRequest(GameGetRequestParameters params) {
    // the place to retrieve filter params from request
    return new GameSearchFilter();
  }
}
