package com.home.ms.aggregator.service.game;

import com.home.ms.aggregator.repository.GameAggregatorEntity;
import com.home.ms.aggregator.repository.GameAggregatorRepository;
import com.home.ms.aggregator.service.exception.NotFoundException;
import com.home.ms.aggregator.service.game.GameAggregatorMapper;
import com.home.ms.aggregator.web.game.GameAggregator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameAggregatorService {
    private final GameAggregatorRepository repository;
    private final GameAggregatorMapper mapper;

    public GameAggregatorService(GameAggregatorRepository repository, GameAggregatorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<GameAggregator> getGameData(String sortBy) {
        Sort by = Sort.by(sortBy).descending();
        return repository.findAll(by).stream().map(mapper::fromEntity).collect(Collectors.toList());
    }

    public GameAggregator createRecord(GameAggregator aggregator) {
        GameAggregatorEntity entity = mapper.toEntity(aggregator);
        repository.save(entity);
        return aggregator;
    }

    public void updatePart(String gameId, GameAggregator delta) {
        GameAggregatorEntity entity = repository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("game", gameId));
        entity.setAchvmtValue(entity.getAchvmtValue() + delta.getAchievementNumber());
        entity.setPlayerValue(entity.getPlayerValue() + delta.getUserNumber());
        repository.save(entity);
    }


}
