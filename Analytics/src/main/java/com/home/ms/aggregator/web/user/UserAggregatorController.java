package com.home.ms.aggregator.web.user;

import com.home.ms.aggregator.service.user.UserAggregatorService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aggregator/users")
public class UserAggregatorController {
    private final UserAggregatorService service;
    private final UserAggregatorValidator validator;

    public UserAggregatorController(UserAggregatorService service, UserAggregatorValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @GetMapping
    public List<UserAggregator> aggregateUsers(@RequestParam @Nullable String sortBy) {
        final String sortByValue = validator.validateSortFieldParameter(sortBy);
        return service.getUserData(sortByValue);
    }

    @PostMapping
    public void createUserRecord(@RequestBody @NonNull UserAggregator aggregator) {
        service.createRecord(aggregator);
    }

    @PatchMapping(value = "/{userId}")
    public void updateUserRecord(@PathVariable String UserId, @RequestBody UserAggregator aggregator) {
        service.updatePart(UserId, aggregator);

    }
}
