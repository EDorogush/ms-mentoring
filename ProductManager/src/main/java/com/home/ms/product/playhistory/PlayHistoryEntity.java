package com.home.ms.product.playhistory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "play_history")
public class PlayHistoryEntity {
  @Id
  @Column(name = "id", length = 36, unique = true)
  private String id;

  @Column(name = "user_id", length = 36, nullable = false)
  private String userId;

  @Column(name = "game_id", length = 36, nullable = false)
  private String gameId;

  /** played time in hours */
  @Column(name = "played_time")
  private long time;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
