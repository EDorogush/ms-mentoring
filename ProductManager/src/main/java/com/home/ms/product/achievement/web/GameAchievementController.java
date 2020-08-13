package com.home.ms.product.achievement.web;


import com.home.ms.product.game.web.GameGetRequestParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games/{gameId}/achievement")
public class GameAchievementController {

    @GetMapping
    public List<Achievement> getUserAchievement(@PathVariable String gameId, GameGetRequestParameters param) {
        return new ArrayList<>();

    }
}

// /games/{}/achievements
// /games/{}/
// /games/{}/play-history
// /games/{}/purchase-history
// /invoices/{}
// /shopping-cart
//
//
//
//
//