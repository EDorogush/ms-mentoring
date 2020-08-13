package com.home.ms.product.playhistory;

public class PlayHistory {
    private String id;
    private long playedHours;

    public long getPlayedHours() {
        return playedHours;
    }

    public void setPlayedHours(long playedHours) {
        this.playedHours = playedHours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

