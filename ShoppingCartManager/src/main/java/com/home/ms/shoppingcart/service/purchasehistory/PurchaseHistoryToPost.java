package com.home.ms.shoppingcart.service.purchasehistory;

import java.math.BigDecimal;
import java.time.Instant;

public class PurchaseHistoryToPost {
  private final String gameId;
  private final Instant date;
  private final BigDecimal cost;

  public PurchaseHistoryToPost(String gameId, Instant date, BigDecimal cost) {
    this.gameId = gameId;
    this.date = date;
    this.cost = cost;
  }

  public String getGameId() {
    return gameId;
  }

  public Instant getDate() {
    return date;
  }

  public BigDecimal getCost() {
    return cost;
  }
}
