package com.backend.allreva.concert.command.domain.value;

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

    private LocalDate stdate;
    private LocalDate eddate;

    private String timeTable;

    @Builder
    public DateInfo(LocalDate stdate, LocalDate eddate, String timeTable) {
        this.stdate = stdate;
        this.eddate = eddate;
        this.timeTable = timeTable;
    }
}
