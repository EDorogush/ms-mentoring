package com.home.ms.product.game.web;

import com.home.ms.product.game.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Game> publishGame(@RequestBody Game gameToPublish) {
        Game game = gameService.publishGame(gameToPublish);
        return ResponseEntity.created(buildLocation(game.getId())).body(game);
    }

    @GetMapping
    public List<Game> searchGames(GameGetRequestParameters parameters) {
        GameSearchFilter filter = new GameSearchFilter();
        getUserId(parameters.getOwnedBy())
                .ifPresentOrElse(userId -> gameService.searchGamesInUserScope(userId, filter), gameService.searchGamesInGeneralScope(filter) );


        filter.setUserId());

        return gameService.searchGamesInUserScope(filter);
    }

    @GetMapping("/{gameId}")
    public Game searchGame(@PathVariable("gameId") String id) {
        return gameService.searchGameById(id);
    }




    private URI buildLocation(String id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    private Optional<String> getUserId(String ownedBy) {
        if (ownedBy == null || !ownedBy.equals("me")) {
            return Optional.ofNullable(ownedBy);
        }
        return Optional.of("user_id-9");

    }
}
