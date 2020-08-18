package com.home.ms.aggregator.web.game;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GamesSortBy {
    GAMEPLAYER("players", "players"),
    ACHIEVEMENTS("achievements", "achievements");

    private static final Map<String, String> BY_REST_NAME =
            Stream.of(GamesSortBy.values()).collect(Collectors.toMap(GamesSortBy::restName, GamesSortBy::entityName));

    private final String restName;
    private final String entityName;

    GamesSortBy(String restName, String entityName) {
        this.restName = restName;
        this.entityName = entityName;
    }

    public static Optional<String> columnNameOf(String restValue) {
        return Optional.ofNullable(BY_REST_NAME.get(restValue));
    }

    public String restName() {
        return restName;
    }

    public String entityName() {
        return entityName;
    }

}
