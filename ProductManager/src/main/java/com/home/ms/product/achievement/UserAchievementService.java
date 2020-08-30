package com.home.ms.product.achievement;

import com.home.ms.product.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAchievementService {
  private final UserAchievementRepository userAchievementRepository;
  private final IdGenerator generator;

  public UserAchievementService(
      UserAchievementRepository userAchievementRepository, IdGenerator generator) {
    this.userAchievementRepository = userAchievementRepository;
    this.generator = generator;
  }

  public List<String> findUserAchievementIds(String userId, String gameId) {
    // todo: still think about user_game_id
    return userAchievementRepository.findAllByUserIdAndGameId(userId, gameId).stream()
        .map(UserAchievementEntity::getAchievementId)
        .collect(Collectors.toList());
  }

  public String addUserAchievementId(String userId, String gameId, String achievementId) {
    UserAchievementEntity entity = new UserAchievementEntity();
    final String id = generator.generateRandomUUID();
    entity.setUserId(userId);
    entity.setGameId(gameId);
    entity.setAchievementId(achievementId);
    entity.setId(id);
    userAchievementRepository.save(entity);
    return id;
  }

  public void deleteAchievement(String userId, String gameId) {
    userAchievementRepository.deleteByUserIdAndGameId(userId, gameId);
  }
}
