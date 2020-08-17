package com.home.ms.product.game.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {

    String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }

}
