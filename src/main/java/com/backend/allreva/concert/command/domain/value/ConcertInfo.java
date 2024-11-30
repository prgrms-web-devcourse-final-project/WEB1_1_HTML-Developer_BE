package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConcertInfo {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConcertStatus prfstate;

    private String host;

    @Embedded
    private DateInfo dateInfo;
}
