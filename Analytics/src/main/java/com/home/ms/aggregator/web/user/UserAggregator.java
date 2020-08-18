package com.home.ms.aggregator.web.user;

import java.math.BigDecimal;

public class UserAggregator {
    private String userId;
    private long gameValue;
    private BigDecimal billValue;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getGameValue() {
        return gameValue;
    }

    public void setGameValue(long gameValue) {
        this.gameValue = gameValue;
    }

    public BigDecimal getBillValue() {
        return billValue;
    }

    public void setBillValue(BigDecimal billValue) {
        this.billValue = billValue;
    }
}
