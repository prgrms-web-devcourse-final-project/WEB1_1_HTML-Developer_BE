package com.backend.allreva.concert.query.application.dto;

import com.backend.allreva.common.model.Image;
import lombok.Getter;

import java.time.LocalDate;

public record ConcertSummaryResponse(

        Image poster,
        String title,

        LocalDate startDate,
        LocalDate endDate,
        String address
) {

}
