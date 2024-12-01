package com.backend.allreva.survey.command.application.dto;

import com.backend.allreva.survey.command.domain.value.Region;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record OpenSurveyRequest(
        String title,
        Long concertId,
        @NotEmpty(message = "날짜는 하루 이상 선택되어야 합니다.")
        List<LocalDate> boardingDates,
        String artistName,
        Region region,
        LocalDate endDate,
        @Min(value = 1, message = "탑승 인원 수는 1명 이상이어야 합니다.")
        int maxPassenger,
        String information
) {
}
