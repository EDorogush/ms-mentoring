package com.home.ms.shoppingcart.web;

import java.math.BigDecimal;

public class ShoppingCartElement {
  private String gameId;
  private BigDecimal price;

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
