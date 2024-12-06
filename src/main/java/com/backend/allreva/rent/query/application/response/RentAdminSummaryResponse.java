package com.backend.allreva.rent.query.application.response;

import com.backend.allreva.rent.command.domain.value.BusSize;
import com.backend.allreva.rent.command.domain.value.BusType;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record RentAdminSummaryResponse(
        Long rentId,
        String title,
        LocalDate boardingDate,
        String boardingArea,
        BusSize busSize,
        BusType busType,
        int maxPassenger,
        LocalDate endDate,
        LocalDateTime createdAt,
        int maxRecruitmentCount,
        int currentRecruitmentCount,
        boolean isClosed
) {

}
