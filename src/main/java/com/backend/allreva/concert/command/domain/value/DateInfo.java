package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "concert_timeTable")
    private String timeTable;

    @Builder
    public DateInfo(LocalDate stdate, LocalDate eddate, String timeTable) {
        this.stdate = stdate;
        this.eddate = eddate;
        this.timeTable = timeTable;
    }
}
