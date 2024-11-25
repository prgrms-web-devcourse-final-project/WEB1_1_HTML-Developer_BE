package com.backend.allreva.survey.command.application;

import com.backend.allreva.concert.command.domain.ConcertRepository;
import com.backend.allreva.concert.command.domain.exception.ConcertNotFoundException;
import com.backend.allreva.survey.command.application.dto.OpenSurveyRequest;
import com.backend.allreva.survey.command.application.dto.SurveyIdResponse;
import com.backend.allreva.survey.command.domain.Survey;
import com.backend.allreva.survey.exception.SurveyNotFoundException;
import com.backend.allreva.survey.exception.SurveyNotWriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SurveyCommandService {
    private final SurveyCommandRepository surveyCommandRepository;
    private final ConcertRepository concertRepository;
    private final SurveyConverter surveyConverter;

    public SurveyIdResponse openSurvey(Long memberId, OpenSurveyRequest openSurveyRequest) {
        checkConcert(openSurveyRequest);

        Survey survey = surveyCommandRepository.save(
                surveyConverter.createSurvey(memberId, openSurveyRequest));
        return new SurveyIdResponse(survey.getId());
    }

    public void removeSurvey(Long memberId, Long surveyId) {
        Survey survey = surveyCommandRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);

        checkWriter(memberId, survey.getMemberId());
        surveyCommandRepository.deleteById(surveyId);
    }

    private void checkConcert(OpenSurveyRequest openSurveyRequest) {
        if (!concertRepository.existsById(openSurveyRequest.concertId()))
            throw new ConcertNotFoundException();
    }

    private void checkWriter(Long memberId, Long writerId) {
        if (!memberId.equals(writerId)) {
            throw new SurveyNotWriterException();
        }
    }

}
