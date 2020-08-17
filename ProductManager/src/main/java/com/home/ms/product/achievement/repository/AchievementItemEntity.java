package com.home.ms.product.achievement.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "achievement_item")
public class AchievementItemEntity {

    /**
     * item's id corresponds to unique pair of userId and gameId and is the same as PurchaseHistory's id.
     * in order to reduce number of inner calls between applications database also contains useId and gameIg data
     */
    @Id
    @Column(name = "id", length = 36, unique = true)
    private String id;

    @Column(name = "user_game_id")
    private String userGameId;

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "game_id", length = 36)
    private String gameId;

    private String achievement;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserGameId() {
        return userGameId;
    }

    public void setUserGameId(String userGameId) {
        this.userGameId = userGameId;
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

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }
}
