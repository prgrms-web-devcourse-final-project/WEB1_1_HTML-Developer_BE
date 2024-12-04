package com.backend.allreva.survey.query.application;

import com.backend.allreva.survey.query.application.dto.SurveyDocumentDto;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.domain.SurveyQueryRepository;
import com.backend.allreva.survey.query.application.dto.SortType;
import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import com.backend.allreva.survey.query.application.dto.SurveySummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurveyQueryService {
    private final SurveyQueryRepository surveyQueryRepository;

    /**
     * 수요조사 상세 조회
     */
    public SurveyDetailResponse findSurveyDetail(final Long surveyId) {
        return surveyQueryRepository.findSurveyDetail(surveyId);
    }

    /**
     * 수요조사 목록 조회
     */
    public List<SurveySummaryResponse> findSurveyList(final Region region,
                                                      final SortType sortType,
                                                      final Long lastId,
                                                      final LocalDate lastEndDate,
                                                      final int pageSize) {
        return surveyQueryRepository.findSurveyList(region, sortType, lastId, lastEndDate, pageSize);
    }

    public Optional<SurveyDocumentDto> findSurveyWithParticipationCount(final Long surveyId) {
        return surveyQueryRepository.findSurveyWithParticipationCount(surveyId);
    }
}
