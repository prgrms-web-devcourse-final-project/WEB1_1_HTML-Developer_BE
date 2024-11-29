package com.backend.allreva.member.query.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class CreatedSurveyResponse {
    private final SurveyResponse surveyResponse;
    private final Integer upCount;
    private final Integer downCount;
    private final Integer roundCount;
}
