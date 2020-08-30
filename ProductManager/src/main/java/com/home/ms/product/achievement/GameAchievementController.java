package com.home.ms.product.achievement;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/games/{gameId}/achievements")
public class GameAchievementController {
  private final AchievementService achievementService;

  public GameAchievementController(AchievementService achievementService) {
    this.achievementService = achievementService;
  }

  @GetMapping
  public List<AchievementItem> searchAchievements(
      @PathVariable String gameId, @RequestParam @Nullable String ownedBy) {
    if (ownedBy == null) {
      return achievementService.findGameAchievements(gameId);
    }
    final String userId = getUserId(ownedBy);
    return achievementService.findUserAchievementsInGame(gameId, userId);
  }

  @PostMapping
  public AchievementItem publishGameAchievement(
      @PathVariable String gameId, @RequestBody AchievementItem item) {
    return achievementService.publishGameAchievement(gameId, item);
  }

  private String getUserId(@NonNull String ownedBy) {
    return "me".equals(Objects.requireNonNull(ownedBy)) ? "user_id-9" : ownedBy;
  }
}
