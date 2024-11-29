package com.backend.allreva.diary.query.dto;

import com.backend.allreva.common.model.Image;

import java.time.LocalDate;

public record DiarySummaryResponse(
        Image concertPoster,
        LocalDate date
) {

}
