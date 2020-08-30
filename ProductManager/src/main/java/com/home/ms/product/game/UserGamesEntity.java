package com.home.ms.product.game;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * represents storage that contain data about of users purchased games. it is needed to decrease the
 * amount of cross requests
 */
@Entity
@Table(name = "user_games")
public class UserGamesEntity {
  @Id
  @Column(name = "id", length = 36, unique = true)
  private String id;

  @Column(name = "user_id", length = 36)
  private String userId;

  @Column(name = "game_id", length = 36)
  private String gameId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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
