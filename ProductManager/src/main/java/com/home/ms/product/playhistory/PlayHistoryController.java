package com.home.ms.product.playhistory;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/games/{gameId}/playHistory")
public class PlayHistoryController {
  private final PlayHistoryService service;

  public PlayHistoryController(PlayHistoryService service) {
    this.service = service;
  }

  @GetMapping
  public PlayHistoryItem getPlayHistory(
      @PathVariable String gameId, @RequestParam @Nullable String ownedBy) {
    final String userId = getUserId(ownedBy);
    return service.search(userId, gameId);
  }

  @PostMapping
  public PlayHistoryItem postPlayHistory(
      @PathVariable String gameId,
      @RequestParam @Nullable String ownedBy,
      @RequestBody PlayHistoryItem playHistory) {
    final String userId = getUserId(ownedBy);
    service.save(playHistory, userId, gameId);
    return playHistory;
  }

  @DeleteMapping
  public void deletePlayHistory(
      @PathVariable String gameId, @RequestParam @Nullable String ownedBy) {
    final String userId = getUserId(ownedBy);
    service.delete(userId, gameId);
  }

  @PatchMapping
  public PlayHistoryItem updatePlayHistory(
      @PathVariable String gameId,
      @RequestParam @Nullable String ownedBy,
      @RequestBody PlayHistoryItem playHistory) {
    final String userId = getUserId(ownedBy);
    service.updatePlayedHours(playHistory, userId, gameId);
    return playHistory;
  }

  private String getUserId(String ownedBy) {
    if (ownedBy == null || !ownedBy.equals("me")) {
      return ownedBy;
    }
    return "user_id-9";
  }
}
