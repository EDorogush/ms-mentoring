package com.home.ms.product.achievement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_achievement_items")
class UserAchievementEntity {
  @Id
  @Column(name = "id", length = 36, unique = true)
  private String id;

  // todo: think about this, is it good to have such value in all application, or just have index in
  // db for quick search
  //  @Column(name = "user_game_id")
  //  private String userGameId;

  @Column(name = "user_id", length = 36, nullable = false)
  private String userId;

  @Column(name = "game_id", length = 36, nullable = false)
  private String gameId;

  @Column(name = "achievement_id", length = 36, nullable = false)
  private String achievementId;

  String getId() {
    return id;
  }

  void setId(String id) {
    this.id = id;
  }

  String getUserId() {
    return userId;
  }

  void setUserId(String userId) {
    this.userId = userId;
  }

  String getGameId() {
    return gameId;
  }

  void setGameId(String gameId) {
    this.gameId = gameId;
  }

  String getAchievementId() {
    return achievementId;
  }

  void setAchievementId(String achievementId) {
    this.achievementId = achievementId;
  }
}
