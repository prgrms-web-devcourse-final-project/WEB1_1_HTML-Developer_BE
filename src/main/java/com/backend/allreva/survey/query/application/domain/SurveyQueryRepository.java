package com.backend.allreva.survey.query.application.domain;

import com.backend.allreva.survey.query.application.dto.SurveyDocumentDto;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.dto.SurveySummaryResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SurveyQueryRepository {
    SurveyDetailResponse findSurveyDetail(final Long surveyId);

    List<SurveySummaryResponse> findSurveyList(final Region region, final SortType sortType,
                                               final Long lastId, final LocalDate lastEndDate, final int pageSize);

    Optional<SurveyDocumentDto> findSurveyWithParticipationCount(final Long surveyId);

    List<SurveySummaryResponse> findSurveyMainList();
}
