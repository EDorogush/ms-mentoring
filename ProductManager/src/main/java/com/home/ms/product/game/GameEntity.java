package com.home.ms.product.game;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "games")
public class GameEntity {
  @Id
  @Column(name = "id", length = 36, unique = true)
  private String id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "base_price")
  private BigDecimal basePrice;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public BigDecimal getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(BigDecimal basePrice) {
    this.basePrice = basePrice;
  }
}
