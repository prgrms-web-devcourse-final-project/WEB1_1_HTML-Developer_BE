package com.kwanse.allreva.concert.command.domain.value;

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
    @Column(name = "concert_stdate")
    private LocalDate stdate;
    @Column(name = "concert_eddate")
    private LocalDate eddate;
    @Enumerated(EnumType.STRING)
    private ConcertStatus prfstate;

}
