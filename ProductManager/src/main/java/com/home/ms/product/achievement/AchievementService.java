package com.home.ms.product.achievement;

import com.home.ms.product.IdGenerator;
import com.home.ms.product.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementService {
  private final UserAchievementService userAchievementService;
  private final AchievementRepository achievementRepository;
  private final AchievementMapper mapper;
  private final IdGenerator generator;

  public AchievementService(
      UserAchievementService userAchievementService,
      AchievementRepository achievementRepository,
      AchievementMapper mapper,
      IdGenerator generator) {
    this.userAchievementService = userAchievementService;
    this.achievementRepository = achievementRepository;
    this.mapper = mapper;
    this.generator = generator;
  }

  /**
   * find all achievements are available in the game
   *
   * @param gameId the ID of game which achievements are searched
   * @return {@link List} of {@link AchievementItem}
   */
  public List<AchievementItem> findGameAchievements(String gameId) {
    return achievementRepository.findAllByGameId(gameId).stream()
        .map(mapper::fromEntity)
        .collect(Collectors.toList());
  }

  public AchievementItem publishGameAchievement(String gameId, AchievementItem item) {
    final String id = generator.generateRandomUUID();
    AchievementEntity entity = mapper.toEntity(item, id, gameId);
    achievementRepository.save(entity);
    return mapper.fromEntity(entity);
  }

  public List<AchievementItem> findUserAchievementsInGame(String gameId, String userId) {
    // todo: still think about user_game_id
    List<String> ids = userAchievementService.findUserAchievementIds(userId, gameId);
    return achievementRepository.findAllById(ids).stream()
        .map(mapper::fromEntity)
        .collect(Collectors.toList());
  }

  public AchievementItem findAchievementById(String id) {
    return achievementRepository
        .findById(id)
        .map(mapper::fromEntity)
        .orElseThrow(() -> new NotFoundException("achievement", id));
  }
}
