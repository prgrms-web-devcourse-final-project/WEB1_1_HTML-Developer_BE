package com.backend.allreva.survey.query.application;

import com.backend.allreva.survey.query.application.dto.SurveyDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyQueryService {
    private final SurveyQueryRepository surveyQueryRepository;

    public SurveyDetailResponse findSurveyDetail(Long surveyId) {
        return surveyQueryRepository.findSurveyDetail(surveyId);
    }
}
