package com.home.ms.product.purchasehistory;

import java.math.BigDecimal;
import java.time.Instant;

public class PurchaseHistoryItem {
  private String id;
  private String gameId;
  private Instant date;
  private BigDecimal cost;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }
}
