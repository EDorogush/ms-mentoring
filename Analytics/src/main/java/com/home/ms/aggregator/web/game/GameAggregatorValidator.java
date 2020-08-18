package com.home.ms.aggregator.web.game;

import com.home.ms.aggregator.service.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class GameAggregatorValidator {
    private static final String MESSAGE_WRONG_SORTBY_VALUE = "wrong sortBy value: {0}";

    public String validateSortFieldParameter(String sortByField) {
        if (sortByField == null) {
            return GamesSortBy.GAMEPLAYER.entityName();
        }
        return GamesSortBy.columnNameOf(sortByField)
                .orElseThrow(() -> new BadRequestException(MessageFormat.format(MESSAGE_WRONG_SORTBY_VALUE, sortByField)));
    }
}
