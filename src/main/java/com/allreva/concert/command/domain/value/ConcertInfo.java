package com.allreva.concert.command.domain.value;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ConcertInfo {

    @Column(name = "concert_title")
    private String title;

    @Column(name = "concert_price")
    private BigDecimal price;

    @Column(name = "concert_hall_id")
    private Long hallId;

    @Column(name = "concert_stdate")
    private LocalDateTime stdate;

    @Column(name = "concert_eddate")
    private LocalDateTime eddate;

    @Enumerated(EnumType.STRING)
    private ConcertStatus prfstate;

    @Embedded
    private Seller seller;
}
