package com.home.ms.product.achievement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** entity represents achievements catalog of games */
@Entity
@Table(name = "achievement_items")
class AchievementEntity {

  @Id
  @Column(name = "item_id", length = 36, unique = true)
  private String itemId;

  @Column(name = "game_id", length = 36, nullable = false)
  private String gameId;

  @Column(name = "title", nullable = false)
  private String achievement;

  String getItemId() {
    return itemId;
  }

  void setItemId(String itemId) {
    this.itemId = itemId;
  }

  String getGameId() {
    return gameId;
  }

  void setGameId(String gameId) {
    this.gameId = gameId;
  }

  String getAchievement() {
    return achievement;
  }

  void setAchievement(String achievement) {
    this.achievement = achievement;
  }
}
