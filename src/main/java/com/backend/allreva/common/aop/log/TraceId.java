package com.backend.allreva.common.aop.log;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceId {
    private final String id;
    private final int level;

    public TraceId() {
        this.id = createdTransactionId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createdTransactionId() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public TraceId createNextId(){
        return new TraceId(id, level + 1);
    }

    public TraceId createPrevId(){
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel(){
        return level == 0;
    }
}
