package com.backend.allreva.survey.infra;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.common.exception.code.GlobalErrorCode;
import com.backend.allreva.search.exception.SearchResultNotFoundException;
import com.backend.allreva.search.infra.SurveyDocumentRepository;
import com.backend.allreva.search.query.application.dto.SurveyDocumentDto;
import com.backend.allreva.search.query.domain.SurveyDocument;
import com.backend.allreva.survey.command.domain.SurveyEvent;
import com.backend.allreva.survey.exception.SurveyEventConsumingException;
import com.backend.allreva.survey.query.application.SurveyQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SurveyConsumer {
    private final SurveyQueryService surveyQueryService;
    private final SurveyDocumentRepository surveyDocumentRepository;
    @Transactional
    @KafkaListener(
            topics = "survey-event",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleSurveyEvent(
            @Payload SurveyEvent event,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ){
        try {
            log.info("이벤트 수신 - partition: {}, offset: {}, event: {}",
                    partition, offset, event);
            handleEventByType(event);
        }catch (Exception e){
            throw new SurveyEventConsumingException();
        }
    }

    private void handleEventByType(final SurveyEvent event) {
        switch (event.getEventType()) {
            case CREATE -> handleCreateEvent(event);
            case UPDATE -> handleUpdateEvent(event);
            case DELETE -> handleDeleteEvent(event);
            case UPDATE_PARTICIPATION_COUNT -> handleUpdateParticipationCount(event);
            default -> log.warn("알 수 없는 이벤트 타입: {}", event.getEventType());
        }
    }

    private void handleCreateEvent(final SurveyEvent event) {
        log.info("설문 생성 이벤트 처리: {}", event.getEventId());

        SurveyDocumentDto surveyDocumentDto = surveyQueryService.
                findSurveyWithParticipationCount(event.getEventId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.BAD_REQUEST_ERROR));

        surveyDocumentRepository.save(SurveyDocumentDto.toSurveyDocument(surveyDocumentDto));
    }

    private void handleUpdateEvent(final SurveyEvent event) {
        log.info("설문 수정 이벤트 처리: {}", event.getEventId());

        SurveyDocument surveyDocument = surveyDocumentRepository
                .findById(event.getEventId().toString())
                .orElseThrow(SearchResultNotFoundException::new);


        surveyDocument.updateSurveyDocument(
                event.getSurvey().title(),
                event.getSurvey().region().toString(),
                event.getSurvey().endDate()
        );

        surveyDocumentRepository.save(surveyDocument);
    }

    private void handleDeleteEvent(final SurveyEvent event) {
        log.info("설문 삭제 이벤트 처리: {}", event.getEventId());

        surveyDocumentRepository.deleteById(event.getEventId().toString());
    }

    private void handleUpdateParticipationCount(final SurveyEvent event) {
        SurveyDocument surveyDocument = surveyDocumentRepository
                .findById(event.getEventId().toString())
                .orElseThrow(SearchResultNotFoundException::new);

        log.info("'participationCount : {}", surveyDocument.getParticipationCount());

        surveyDocument.updateParticipationCount(event.getSurvey().participationCount());

        surveyDocumentRepository.save(surveyDocument);
    }
}
