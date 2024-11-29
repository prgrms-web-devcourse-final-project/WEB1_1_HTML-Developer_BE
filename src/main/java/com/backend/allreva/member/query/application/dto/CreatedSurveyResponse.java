package com.backend.allreva.member.query.application.dto;


import lombok.Getter;

@Getter
public class CreatedSurveyResponse {

    SurveyResponse surveyResponse;
    Integer upCount;
    Integer downCount;
    Integer roundCount;

    public CreatedSurveyResponse(SurveyResponse surveyResponse, Integer upCount, Integer downCount, Integer roundCount) {
        this.surveyResponse = surveyResponse;
        this.upCount = upCount;
        this.downCount = downCount;
        this.roundCount = roundCount;
    }

    @Override
    public String toString() {
        return "CreatedSurveyResponse{" +
                "surveyResponse=" + surveyResponse +
                ", upCount=" + upCount +
                ", downCount=" + downCount +
                ", roundCount=" + roundCount +
                '}';
    }
}
