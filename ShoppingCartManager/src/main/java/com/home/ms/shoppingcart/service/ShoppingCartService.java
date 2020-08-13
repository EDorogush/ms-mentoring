package com.home.ms.shoppingcart.service;

import com.home.ms.shoppingcart.repository.ShoppingCartEntity;
import com.home.ms.shoppingcart.repository.ShoppingCartRepository;
import com.home.ms.shoppingcart.web.ShoppingCartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository repository;

    public ShoppingCartService(ShoppingCartRepository repository) {
        this.repository = repository;
    }

    public List<ShoppingCartItem> getItems(String userId) {
        List<ShoppingCartEntity> entities = repository.findByUserId(userId);
        return entities.stream().map(enity -> {
            ShoppingCartItem item = new ShoppingCartItem();
            item.setId(enity.getId());
            item.setGameId(enity.getGameId());
            return item;
        }).collect(Collectors.toList());
    }

    public ShoppingCartItem addItem(String userId, ShoppingCartItem shoppingCartItem) {
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setId(UUID.randomUUID().toString());
        shoppingCartEntity.setGameId(shoppingCartItem.getGameId());
        shoppingCartEntity.setUserId(userId);
        repository.save(shoppingCartEntity);
        shoppingCartItem.setId(shoppingCartEntity.getId());
        return shoppingCartItem;

    }

    public void removeItemById(String itemId) {
        repository.deleteById(itemId);
    }


    public void cleanUp(String userId) {

    }
}
