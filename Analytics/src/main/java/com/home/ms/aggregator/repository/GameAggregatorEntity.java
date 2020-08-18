package com.home.ms.aggregator.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class GameAggregatorEntity {
    @Id
    @Column(name = "gameId", length = 36, unique = true)
    private String gameId;

    @Column(name = "players")
    private long playerValue;

    @Column(name = "achievements")
    private long achvmtValue;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public long getPlayerValue() {
        return playerValue;
    }

    public void setPlayerValue(long playerValue) {
        this.playerValue = playerValue;
    }

    public long getAchvmtValue() {
        return achvmtValue;
    }

    public void setAchvmtValue(long achvmtValue) {
        this.achvmtValue = achvmtValue;
    }
}
