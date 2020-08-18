package com.home.ms.aggregator.service.user;

import com.home.ms.aggregator.repository.UserAggregatorEntity;
import com.home.ms.aggregator.web.user.UserAggregator;

public class UserAggregatorMapper {
    public UserAggregatorEntity toEntity(UserAggregator item) {
        UserAggregatorEntity entity = new UserAggregatorEntity();
        entity.setUserId(item.getUserId());
        entity.setGameValue(item.getGameValue());
        entity.setBill(item.getBillValue());
        return entity;
    }

    public UserAggregator fromEntity(UserAggregatorEntity entity) {
        UserAggregator item = new UserAggregator();
        item.setUserId(entity.getUserId());
        item.setGameValue(entity.getGameValue());
        item.setBillValue(entity.getBill());
        return item;
    }
}
