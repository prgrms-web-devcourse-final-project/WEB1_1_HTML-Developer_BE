package com.backend.allreva.survey.infra.rdb;

import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.response.SortType;
import com.backend.allreva.survey.query.application.response.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.response.SurveyDocumentDto;
import com.backend.allreva.survey.query.application.response.SurveyMainResponse;
import com.backend.allreva.survey.query.application.response.SurveySummaryResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SurveyDslRepository {
    SurveyDetailResponse findSurveyDetail(Long surveyId);

    List<SurveySummaryResponse> findSurveyList(Region region,
                                               SortType sortType,
                                               Long lastId,
                                               LocalDate lastEndDate,
                                               int pageSize);

    List<SurveySummaryResponse> findSurveyMainList();

    Optional<SurveyMainResponse> findSurveyWithParticipationCount(Long surveyId);
}
