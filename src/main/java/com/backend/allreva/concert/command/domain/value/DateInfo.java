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

    @Column(nullable = false)
    private LocalDate stdate;
    @Column(nullable = false)
    private LocalDate eddate;

    @Column(nullable = false)
    private String timeTable;

    @Builder
    public DateInfo(LocalDate stdate, LocalDate eddate, String timeTable) {
        this.stdate = stdate;
        this.eddate = eddate;
        this.timeTable = timeTable;
    }
}
