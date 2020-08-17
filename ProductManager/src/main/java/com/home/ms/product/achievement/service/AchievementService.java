package com.home.ms.product.achievement.service;

import com.home.ms.product.achievement.repository.AchievementItemEntity;
import com.home.ms.product.achievement.repository.AchievementRepository;
import com.home.ms.product.achievement.web.AchievementItem;
import com.home.ms.product.game.service.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementService {
    private final AchievementRepository repository;
    private final AchievementMapper mapper;

    public AchievementService(AchievementRepository repository, AchievementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AchievementItem> findUserAchievementsInGame(long userGameId) {
        return repository.findAllByUserGameId(userGameId).stream().map(mapper::fromEntity).collect(Collectors.toList());
    }

    public AchievementItem findAchievementById(String id) {
        final AchievementItemEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("achievement", id));
        return mapper.fromEntity(entity);
    }

    public AchievementItem addUserAchievement(AchievementItem item, String userId) {
        return new AchievementItem();


    }
}
