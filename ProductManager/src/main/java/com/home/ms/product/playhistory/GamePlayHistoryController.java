package com.home.ms.product.playhistory;

import com.home.ms.product.game.web.GameGetRequestParameters;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/games/{gameId}/playHistory")
public class GamePlayHistoryController {

    @GetMapping
    public PlayHistory getPlayHistory(@PathVariable String gameId, GameGetRequestParameters params) {
        return new PlayHistory();
    }

    @PostMapping
    public PlayHistory postPlayHistory(@PathVariable String gameId, @RequestBody PlayHistory playHistory) {
        return playHistory;
    }

    @DeleteMapping
    public void deletePlayHistory(@PathVariable String gameId, GameGetRequestParameters param) {
    }

    @PutMapping
    public PlayHistory updatePlayHistory(@PathVariable String gameId, @RequestBody PlayHistory playHistory) {
        return playHistory;
    }
}
