package com.backend.allreva.rent.query.application.dto;

import java.time.LocalDate;

public record RentSummaryResponse(
        Long rentId,
        String title,
        String boardingArea,
        LocalDate endDate,
        String imageUrl
) {

}
