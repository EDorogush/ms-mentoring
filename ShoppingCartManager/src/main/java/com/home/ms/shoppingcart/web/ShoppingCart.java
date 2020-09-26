package com.home.ms.shoppingcart.web;

import java.util.List;
import java.util.Objects;

public class ShoppingCart {
  private String status;
  private List<ShoppingCartElement> gameIds;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<ShoppingCartElement> getGameIds() {
    return gameIds;
  }

  public void setGameIds(List<ShoppingCartElement> gameIds) {
    this.gameIds = gameIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ShoppingCart that = (ShoppingCart) o;
    return Objects.equals(status, that.status) && Objects.equals(gameIds, that.gameIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, gameIds);
  }
}
