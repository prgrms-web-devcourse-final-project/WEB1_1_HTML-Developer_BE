package com.backend.allreva.survey.command.application.dto;

import com.backend.allreva.survey.command.domain.value.Region;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record UpdateSurveyRequest(
        @NotNull
        Long surveyId,
        @NotBlank
        String title,
        @NotEmpty(message = "날짜는 하루 이상 선택되어야 합니다.")
        List<LocalDate> boardingDates,
        @NotNull
        Region region,
        @FutureOrPresent(message = "마감 기한은 과거일 수 없습니다.")
        LocalDate endDate,
        @NotNull
        @Min(value = 1, message = "탑승 인원 수는 1명 이상이어야 합니다.")
        int maxPassenger,
        String information
) {
}
