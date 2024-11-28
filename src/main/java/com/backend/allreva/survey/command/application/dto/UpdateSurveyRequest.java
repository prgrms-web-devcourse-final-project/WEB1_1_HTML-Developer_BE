package com.backend.allreva.survey.command.application.dto;

import com.backend.allreva.survey.command.domain.value.Region;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record UpdateSurveyRequest(
        String title,
        @NotEmpty(message = "날짜는 하루 이상 선택되어야 합니다.")
        List<String> boardingDate, //형식은 2024.01.02(금)
        Region region,
        LocalDate eddate,
        @Min(value = 0, message = "탑승 인원 수는 0명 이상이어야 합니다.")
        int maxPassenger,
        String information
) {
}