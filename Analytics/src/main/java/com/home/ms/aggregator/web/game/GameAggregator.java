package com.home.ms.aggregator.web.game;

public class GameAggregator {
    private String gameId;
    private long userNumber;
    private long achievementNumber;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(long userNumber) {
        this.userNumber = userNumber;
    }

    public long getAchievementNumber() {
        return achievementNumber;
    }

    public void setAchievementNumber(long achievementNumber) {
        this.achievementNumber = achievementNumber;
    }
}
