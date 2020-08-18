package com.home.ms.aggregator.web.game;

import com.home.ms.aggregator.service.game.GameAggregatorService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aggregator/games")
public class GameAggregatorController {
    private final GameAggregatorValidator validator;
    private final GameAggregatorService service;

    public GameAggregatorController(GameAggregatorValidator validator, GameAggregatorService service) {
        this.validator = validator;
        this.service = service;
    }

    @GetMapping
    public List<GameAggregator> aggregateGames(@RequestParam @Nullable String sortBy) {
        final String sortByValue = validator.validateSortFieldParameter(sortBy);
        return service.getGameData(sortByValue);
    }

    @PostMapping
    public void createGameRecord(@RequestBody @NonNull GameAggregator aggregator) {
        service.createRecord(aggregator);
    }

    @PatchMapping(value = "/{gameId}")
    public void updateGameRecord(@PathVariable String gameId, @RequestBody GameAggregator aggregator) {
        service.updatePart(gameId, aggregator);

    }


}
