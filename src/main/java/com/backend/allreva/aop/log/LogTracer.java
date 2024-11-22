package com.backend.allreva.aop.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogTracer {

    private static final String PREFIX = "-->";
    private static final String SUFFIX = "<--";
    private static final String ERR_FIX = "<X-";

    private final ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    public TraceStatus begin(String message, String args) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        if (traceId.isFirstLevel()) {
            log.info("--------------------- [TraceId: {}] start ---------------------",
                    traceId.getId());
        }
        log.info("[{}] {}{}, args = {}", traceId.getId(), addSpace(PREFIX, traceId.getLevel()), message, args);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceId.createNextId());
        }
    }

    public void end(TraceStatus traceStatus) {
        complete(traceStatus, null);
    }

    public void handleException(TraceStatus traceStatus, Exception ex) {
        complete(traceStatus, ex);
    }

    private void complete(TraceStatus traceStatus, Exception ex) {
        Long stopTimeMs = System.currentTimeMillis();
        Long resultTimeMs = stopTimeMs - traceStatus.getStartTimesMs();
        TraceId traceId = traceStatus.getTraceId();
        if (ex == null) {
            log.info("[{}] {}{} time = {}ms", traceId.getId(), addSpace(SUFFIX, traceId.getLevel()),
                    traceStatus.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {} {} time = {}ms ex={}", traceId.getId(), addSpace(ERR_FIX, traceId.getLevel()),
                    traceStatus.getMessage(), resultTimeMs, ex.toString());
        }
        if (traceStatus.getTraceId().isFirstLevel()) {
            log.info("--------------------- [TraceId: {}] end Time: {} ms ---------------------",
                    traceId.getId(), resultTimeMs);
        }
        releaseTraceId();
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) {
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceId.createPrevId());
        }
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
