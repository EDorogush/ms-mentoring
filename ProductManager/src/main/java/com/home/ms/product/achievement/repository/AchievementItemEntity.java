package com.home.ms.product.achievement.repository;

public class AchievementItemEntity {

    private String id;
    private long userGameRecordId;
    private String achievement;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUserGameRecordId() {
        return userGameRecordId;
    }

    public void setUserGameRecordId(long userGameRecordId) {
        this.userGameRecordId = userGameRecordId;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }
}
