package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Column(name = "concert_stdate")
    private LocalDate stdate;
    @Column(name = "concert_eddate")
    private LocalDate eddate;
    @Column(name = "concert_prfstate")
    @Enumerated(EnumType.STRING)
    private ConcertStatus prfstate;
    @Column(name = "concert_timeTable")
    private String timeTable;
    @Column(name = "concert_host")
    private String host;
}
