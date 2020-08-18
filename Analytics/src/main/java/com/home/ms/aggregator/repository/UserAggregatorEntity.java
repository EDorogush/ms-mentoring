package com.home.ms.aggregator.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class UserAggregatorEntity {
    @Id
    @Column(name = "user_id", length = 36, unique = true)
    private String userId;

    @Column(name = "games")
    private long gameValue;

    @Column(name = "bill")
    private BigDecimal bill;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getGameValue() {
        return gameValue;
    }

    public void setGameValue(long gameValue) {
        this.gameValue = gameValue;
    }

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }
}
