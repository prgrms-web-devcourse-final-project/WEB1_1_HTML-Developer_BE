package com.backend.allreva.survey.command.application.dto;

import com.backend.allreva.survey.command.domain.value.BoardingType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record JoinSurveyRequest(
        @NotNull
        Long surveyId,
        @NotNull
        LocalDate boardingDate,
        @NotNull
        BoardingType boardingType,
        @NotNull
        @Min(value = 1, message = "탑승 인원 수는 1명 이상이어야 합니다.")
        int passengerNum,
        boolean notified
) {
}
