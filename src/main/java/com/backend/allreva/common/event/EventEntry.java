package com.backend.allreva.common.event;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"entity_id", "entity_type"})
)
@Entity
public class EventEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    private String entityId;

    @Getter
    @Column(updatable = false)
    private Long timestamp;

    @Builder
    private EventEntry(
            final EntityType entityType,
            final String entityId,
            final Long timestamp
    ) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.timestamp = timestamp;
    }
}