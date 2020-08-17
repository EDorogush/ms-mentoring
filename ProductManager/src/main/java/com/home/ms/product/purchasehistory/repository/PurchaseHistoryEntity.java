package com.home.ms.product.purchasehistory.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "purchase_history")
public class PurchaseHistoryEntity {
    @Id
    @Column(name = "id", length = 36, unique = true)
    private String id;

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "game_id", length = 36)
    private String gameId;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "date")
    private Instant date;

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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }
}
