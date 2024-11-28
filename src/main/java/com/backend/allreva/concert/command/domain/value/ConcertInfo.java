package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConcertInfo {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String price;

    @Enumerated(EnumType.STRING)
    private ConcertStatus prfstate;

    private String host;

    @Embedded
    private DateInfo dateInfo;
}
