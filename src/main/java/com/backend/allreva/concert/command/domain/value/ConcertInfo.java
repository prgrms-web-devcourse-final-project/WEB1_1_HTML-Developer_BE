package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConcertInfo {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String price;

    @Column(nullable = false, name = "prfstate")
    @Enumerated(EnumType.STRING)
    private ConcertStatus performStatus;

    private String host;

    @Embedded
    private DateInfo dateInfo;

    @Builder
    private ConcertInfo(
            final String title,
            final String price,
            final ConcertStatus performStatus,
            final String host,
            final DateInfo dateInfo
    ) {
        this.title = title;
        this.price = price;
        this.performStatus = performStatus;
        this.host = host;
        this.dateInfo = dateInfo;
    }
}
