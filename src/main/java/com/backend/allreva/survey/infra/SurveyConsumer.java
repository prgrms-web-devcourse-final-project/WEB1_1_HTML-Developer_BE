package com.backend.allreva.survey.infra;

import com.backend.allreva.common.exception.CustomException;
import com.backend.allreva.common.exception.code.GlobalErrorCode;
import com.backend.allreva.search.infra.SurveyDocumentRepository;
import com.backend.allreva.search.query.application.dto.SurveyDocumentDto;
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

            switch (event.getEventType()){
                case CREATE:
                    handleCreateEvent(event);
                    break;

                case UPDATE:
                    handleUpdateEvent(event);
                    break;

                case DELETE:
                    handleDeleteEvent(event);
                    break;
                default:
                    log.warn("알 수 없는 이벤트 타입: {}", event.getEventType());
            }
        }catch (Exception e){
            throw new SurveyEventConsumingException();
        }
    }

    private void handleCreateEvent(SurveyEvent event) {
        SurveyDocumentDto surveyDocumentDto = surveyQueryService.
                findSurveyWithParticipationCount(event.getEventId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.BAD_REQUEST_ERROR));

        surveyDocumentRepository.save(SurveyDocumentDto.toSurveyDocument(surveyDocumentDto));

        log.info("설문 생성 이벤트 처리: {}", event.getEventId());
    }

    private void handleUpdateEvent(SurveyEvent event) {
        log.info("설문 수정 이벤트 처리: {}", event.getEventId());
        // 설문 수정 로직
    }

    private void handleDeleteEvent(SurveyEvent event) {
        log.info("설문 삭제 이벤트 처리: {}", event.getEventId());
        // 설문 삭제 로직
    }
}
