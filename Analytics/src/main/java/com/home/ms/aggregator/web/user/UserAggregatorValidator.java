package com.home.ms.aggregator.web.user;

import com.home.ms.aggregator.service.exception.BadRequestException;
import com.home.ms.aggregator.web.game.GamesSortBy;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class UserAggregatorValidator {
    private static final String MESSAGE_WRONG_SORTBY_VALUE = "wrong sortBy value: {0}";

    public String validateSortFieldParameter(String sortByField) {
        if (sortByField == null) {
            return GamesSortBy.GAMEPLAYER.entityName();
        }
        return GamesSortBy.columnNameOf(sortByField)
                .orElseThrow(() -> new BadRequestException(MessageFormat.format(MESSAGE_WRONG_SORTBY_VALUE, sortByField)));
    }
}
