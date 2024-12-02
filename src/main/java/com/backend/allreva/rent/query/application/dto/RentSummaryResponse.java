package com.backend.allreva.rent.query.application.dto;

import java.time.LocalDate;

public record RentSummaryResponse(
        String imageUrl,
        String title,
        String boardingArea,
        int recruitmentCount,
        int currentRecruitmentCount,
        LocalDate endDate
) {

}
