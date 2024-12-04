package com.backend.allreva.common.event;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "idx_event_created_at", columnList = "created_at"))
@Entity
public class EventEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private EventEntry(
            final String topic,
            final String payload
    ) {
        this.topic = topic;
        this.payload = payload;
        this.createdAt = LocalDateTime.now();
    }
}
