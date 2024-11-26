package com.backend.allreva.survey.query.application;

import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;

public interface SurveyQueryRepository {
    SurveyDetailResponse findSurveyDetail(Long surveyId);
}
