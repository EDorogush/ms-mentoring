package com.home.ms.shoppingcart.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "shopping_cart_elements")
public class ShoppingCartEntityElement {
  @Id
  @Column(name = "id", length = 36, unique = true)
  private String id;

  @Column(name = "user_id", length = 36)
  private String userId;

  @Column(name = "game_id", length = 36)
  private String gameId;

  @Column(name = "price", precision=10, scale=6)
  private BigDecimal price;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

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
