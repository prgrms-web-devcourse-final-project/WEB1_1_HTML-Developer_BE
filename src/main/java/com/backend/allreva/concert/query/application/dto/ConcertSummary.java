package com.backend.allreva.concert.query.application.dto;

import com.backend.allreva.common.model.Image;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ConcertSummary {

    // 포스터, 제목, DateInfo(시작, 종료), 장소
    private Image poster;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String address;

    public ConcertSummary(
            Image poster,
            String title,
            LocalDate startDate,
            LocalDate endDate,
            String address
    ) {
        this.poster = poster;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
    }
}
