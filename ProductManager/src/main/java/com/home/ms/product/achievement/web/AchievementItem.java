package com.home.ms.product.achievement.web;

public class AchievementItem {
    /**
     * item's id corresponds to unique pair of userId and gameId and is the same as PurchaseHistory's id.
     */
    private String id;
    private String gameId;
    private String achievement;

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

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
}
