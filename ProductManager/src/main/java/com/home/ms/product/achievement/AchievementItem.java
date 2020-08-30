package com.home.ms.product.achievement;

public class AchievementItem {

  private String id;

  // todo: we get items through /{gameId} endpoint, do we need to duplicate gameId in entity?
  // private String gameId;
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
}
