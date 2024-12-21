package com.backend.allreva.common.event;

public interface EventEntryDslRepository {
    boolean isValidEvent(
            EntityType entityType,
            String entityId,
            Long timestamp
    );
}
