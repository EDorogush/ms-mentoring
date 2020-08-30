package com.home.ms.product.achievement;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games/{gameId}/achievements/{achievementId}")
public class UserAchievementController {
  private final UserAchievementService service;

  public UserAchievementController(UserAchievementService userAchievementService) {
    this.service = userAchievementService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String addAchievementForUser(
      @PathVariable String gameId,
      @PathVariable String achievementId,
      @RequestBody UserAchievementItem item) {
    return service.addUserAchievementId(item.getUserId(), gameId, achievementId);
  }

  @DeleteMapping
  public void takeAwayAchievement(
      @PathVariable String gameId,
      @PathVariable String achievementId,
      @RequestParam @NonNull String ownedBy) {
    // todo:test nullable
    service.deleteAchievement(ownedBy, achievementId);
  }
}
