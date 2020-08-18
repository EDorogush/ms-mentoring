package com.home.ms.aggregator.web.user;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum UsersSortBy {

    GAMES("games", "gameValue"),

    BILL("billValue", "billValue");

    private static final Map<String, String> BY_REST_NAME =
            Stream.of(UsersSortBy.values()).collect(Collectors.toMap(UsersSortBy::restName, UsersSortBy::entityName));

    private final String restName;
    private final String entityName;

    UsersSortBy(String restName, String entityName) {
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


