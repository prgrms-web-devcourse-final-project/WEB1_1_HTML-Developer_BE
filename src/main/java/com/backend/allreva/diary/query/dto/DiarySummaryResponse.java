package com.backend.allreva.diary.query.dto;

import com.backend.allreva.common.model.Image;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public final class DiarySummaryResponse {

    private final Image concertPoster;
    private final LocalDate date;

    public DiarySummaryResponse(
            final Image concertPoster,
            final LocalDate date
    ) {
        this.concertPoster = concertPoster;
        this.date = date;
    }
}
