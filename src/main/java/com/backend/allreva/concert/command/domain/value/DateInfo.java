package com.backend.allreva.concert.command.domain.value;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DateInfo {

    @Column(nullable = false, name = "stdate")
    private LocalDate startDate;
    @Column(nullable = false, name = "eddate")
    private LocalDate endDate;

    @Column(nullable = false)
    private String timeTable;

    @Builder
    private DateInfo(LocalDate startDate, LocalDate endDate, String timeTable) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeTable = timeTable;
    }
}
