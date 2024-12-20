package com.backend.allreva.common.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EventEntryRepository extends JpaRepository<EventEntry, Long> {

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO event_entry (entity_id, entity_type, timestamp) " +
                    "VALUES (:entityId, :entityType, :timestamp) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "timestamp = IF(:timestamp > timestamp, :timestamp, timestamp)",
            nativeQuery = true
    )
    int upsert(
            @Param("entityType") String entityType,
            @Param("entityId") String entityId,
            @Param("timestamp") Long timestamp
    );
}
