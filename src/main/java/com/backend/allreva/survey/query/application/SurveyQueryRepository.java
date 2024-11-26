package com.backend.allreva.survey.query.application;

import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.dto.SurveySummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface SurveyQueryRepository {
    SurveyDetailResponse findSurveyDetail(Long surveyId);

    List<SurveySummaryResponse> findSurveyList(Region region, SortType sortType, Long lastId, LocalDate lastEndDate, int pageSize);

}
