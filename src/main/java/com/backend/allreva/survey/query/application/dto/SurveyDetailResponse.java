package com.backend.allreva.survey.query.application.dto;

import com.backend.allreva.common.converter.DataConverter;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SurveyDetailResponse {
    final Long surveyId;
    final String title;
    final List<String> boardingDate;
    final String information;

    public SurveyDetailResponse(Long surveyId, String title, List<LocalDate> boardingDate, String information) {
        this.surveyId = surveyId;
        this.title = title;
        this.boardingDate = boardingDate.stream()
                .map(DataConverter::convertToDateWithDayFromLocalDate)
                .collect(Collectors.toList());
        this.information = information;
    }
}
