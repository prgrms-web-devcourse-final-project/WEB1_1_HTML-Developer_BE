package com.backend.allreva.member.query.application.dto;

import com.backend.allreva.rent.command.domain.value.BoardingType;

import java.time.LocalDate;

public record JoinSurveyResponse(
        SurveyResponse surveyResponse,
        LocalDate participationDate,
        LocalDate boardingDate,
        BoardingType boardingType,
        int participationCount
) {
}
