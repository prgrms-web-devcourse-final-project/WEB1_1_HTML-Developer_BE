package com.backend.allreva.common.event;

import lombok.Getter;

@Getter
public abstract class Event {

    private final long timestamp;
    private boolean isReissued;

    protected Event() {
        this.timestamp = System.currentTimeMillis();
        this.isReissued = false;
    }

    public void markAsReissued() {
        this.isReissued = true;
    }
}
