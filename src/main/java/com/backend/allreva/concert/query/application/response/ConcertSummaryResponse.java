package com.backend.allreva.concert.query.application.response;

import com.backend.allreva.common.model.Image;

import java.time.LocalDate;

public record ConcertSummaryResponse(

        Image poster,
        String title,

        LocalDate startDate,
        LocalDate endDate,
        String address
) {

}
