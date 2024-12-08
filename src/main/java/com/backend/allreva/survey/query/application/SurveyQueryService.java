package com.backend.allreva.survey.query.application;

import com.backend.allreva.survey.command.domain.SurveyRepository;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.response.SortType;
import com.backend.allreva.survey.query.application.response.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.response.SurveyMainResponse;
import com.backend.allreva.survey.query.application.response.SurveySummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurveyQueryService {
    private final SurveyRepository surveyRepository;

    /**
     * 수요조사 상세 조회
     */
    public SurveyDetailResponse findSurveyDetail(final Long surveyId) {
        return surveyRepository.findSurveyDetail(surveyId);
    }

    /**
     * 수요조사 목록 조회
     */
    public List<SurveySummaryResponse> findSurveyList(final Region region,
                                                      final SortType sortType,
                                                      final Long lastId,
                                                      final LocalDate lastEndDate,
                                                      final int pageSize) {
        return surveyRepository.findSurveyList(region, sortType, lastId, lastEndDate, pageSize);
    }

    public Optional<SurveyMainResponse> findSurveyWithParticipationCount(final Long surveyId) {
        return surveyRepository.findSurveyWithParticipationCount(surveyId);
    }

    public List<SurveySummaryResponse> findSurveyMainList() {
        return surveyRepository.findSurveyMainList();
    }
}
