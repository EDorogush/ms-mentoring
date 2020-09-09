package com.home.ms.shoppingcart.service;

import com.home.ms.shoppingcart.repository.ShoppingCartEntity;
import com.home.ms.shoppingcart.repository.ShoppingCartEntityElement;
import com.home.ms.shoppingcart.web.ShoppingCart;
import com.home.ms.shoppingcart.web.ShoppingCartElement;

import java.util.stream.Collectors;

public class ShoppingCartMapper {

  ShoppingCart fromEntity(ShoppingCartEntity entity) {
    ShoppingCart item = new ShoppingCart();
    item.setStatus(entity.getStatus().getValue());
    item.setGameIds(
        entity.getElements().stream().map(this::fromEntityElement).collect(Collectors.toList()));
    return item;
  }

  ShoppingCartEntityElement toEntityElement(ShoppingCartElement itemElement) {
    ShoppingCartEntityElement entityElement = new ShoppingCartEntityElement();
    entityElement.setGameId(itemElement.getGameId());
    entityElement.setPrice(itemElement.getPrice());
    return entityElement;
  }

  ShoppingCartElement fromEntityElement(ShoppingCartEntityElement entityElement) {
    ShoppingCartElement itemElement = new ShoppingCartElement();
    itemElement.setGameId(entityElement.getGameId());
    itemElement.setPrice(entityElement.getPrice());
    return itemElement;
  }
}
