package com.backend.allreva.survey_join.infra.rdb;

import com.backend.allreva.survey.query.application.response.CreatedSurveyResponse;
import com.backend.allreva.survey_join.query.application.response.JoinSurveyResponse;

import java.time.LocalDate;
import java.util.List;

public interface SurveyJoinDslRepository {
    List<CreatedSurveyResponse> getCreatedSurveyList(final Long memberId, final Long lastId, final LocalDate lastBoardingDate, final int pageSize);

    List<JoinSurveyResponse> getJoinSurveyList(final Long memberId, final Long lastId, final int pageSize);

}
