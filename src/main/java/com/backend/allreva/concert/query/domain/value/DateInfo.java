package com.backend.allreva.concert.query.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DateInfo {

    @Column(name = "concert_stdate")
    private LocalDate stdate;

    @Column(name = "concert_eddate")
    private LocalDate eddate;
}
