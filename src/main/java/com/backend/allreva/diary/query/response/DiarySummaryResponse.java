package com.backend.allreva.diary.query.response;

import com.backend.allreva.common.model.Image;

import java.time.LocalDate;

public record DiarySummaryResponse(
        Long diaryId,
        Image concertPoster,
        LocalDate date
) {

}
