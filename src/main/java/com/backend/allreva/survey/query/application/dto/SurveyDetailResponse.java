package com.backend.allreva.survey.query.application.dto;

import java.time.LocalDate;
import java.util.List;

public record SurveyDetailResponse(Long surveyId,
                                   String title,
                                   List<LocalDate> boardingDates,
                                   String information,
                                   boolean isClosed) {
}
