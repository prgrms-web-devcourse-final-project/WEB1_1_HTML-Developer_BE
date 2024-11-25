package com.backend.allreva.survey.command.application.dto;

import com.backend.allreva.survey.command.domain.value.Region;

import java.time.LocalDate;
import java.util.List;

public record OpenSurveyRequest(
        String title,
        Long concertId,
        List<String> boardingDate, //형식은 2024.01.02(금)
        String artistName,
        Region region,
        LocalDate eddate,
        int maxPassenger,
        String information
) {
}
