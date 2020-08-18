package com.home.ms.product.achievement.web;


import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games/{gameId}/achievements")
public class GameAchievementController {

    @GetMapping
    public List<AchievementItem> getUserAchievement(@PathVariable String gameId, @RequestParam @Nullable String userId) {
        return new ArrayList<>();
    }

    @PostMapping
    public AchievementItem addAchievementForUser(@PathVariable String gameId, @RequestParam @Nullable String userIdm,
                                                 @RequestBody AchievementItem item) {
        return new AchievementItem();
    }

    @GetMapping
    @RequestMapping("/{itemId}")
    public AchievementItem findById(@PathVariable String gameId, @PathVariable String itemId) {
        return new AchievementItem();
    }

    @DeleteMapping
    @RequestMapping("/{itemId}")
    public void deleteItem(@PathVariable String gameId, @PathVariable String itemId) {

    }
}
