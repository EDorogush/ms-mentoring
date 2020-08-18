package com.home.ms.aggregator.service.user;

import com.home.ms.aggregator.repository.UserAggregatorEntity;
import com.home.ms.aggregator.repository.UserAggregatorRepository;
import com.home.ms.aggregator.service.exception.NotFoundException;
import com.home.ms.aggregator.web.user.UserAggregator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAggregatorService {
    private final UserAggregatorRepository repository;
    private final UserAggregatorMapper mapper;

    public UserAggregatorService(UserAggregatorRepository repository, UserAggregatorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<UserAggregator> getUserData(String sortBy) {
        Sort by = Sort.by(sortBy).descending();
        return repository.findAll(by).stream().map(mapper::fromEntity).collect(Collectors.toList());
    }

    public UserAggregator createRecord(UserAggregator aggregator) {
        UserAggregatorEntity entity = mapper.toEntity(aggregator);
        repository.save(entity);
        return aggregator;
    }

    public void updatePart(String UserId, UserAggregator delta) {
        UserAggregatorEntity entity = repository.findById(UserId)
                .orElseThrow(() -> new NotFoundException("User", UserId));
        entity.setGameValue(entity.getGameValue() + delta.getGameValue());
        entity.setBill(entity.getBill().add(delta.getBillValue()));
        repository.save(entity);
    }


}
