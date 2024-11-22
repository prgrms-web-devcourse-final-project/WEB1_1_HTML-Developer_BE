package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConcertInfo {
    @Column(name = "concert_title")
    private String title;
    @Column(name = "concert_price",columnDefinition = "TEXT")
    private String price;
    @Column(name = "concert_prfstate")
    @Enumerated(EnumType.STRING)
    private ConcertStatus prfstate;
    @Column(name = "concert_host")
    private String host;

    @Embedded
    private DateInfo dateInfo;
}
