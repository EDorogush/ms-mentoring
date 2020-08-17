package com.home.ms.product.purchasehistory.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {

    public String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
