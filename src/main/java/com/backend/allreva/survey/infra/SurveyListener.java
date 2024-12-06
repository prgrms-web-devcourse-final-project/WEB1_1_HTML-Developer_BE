package com.backend.allreva.survey.infra;

import com.backend.allreva.common.event.JsonParsingError;
import com.backend.allreva.common.exception.NotFoundException;
import com.backend.allreva.survey.command.domain.SurveyDeletedEvent;
import com.backend.allreva.surveyJoin.command.domain.SurveyJoinEvent;
import com.backend.allreva.survey.command.domain.SurveySavedEvent;
import com.backend.allreva.survey.infra.elasticsearch.SurveyDocument;
import com.backend.allreva.survey.infra.elasticsearch.SurveyDocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.backend.allreva.survey.command.domain.SurveyDeletedEvent.TOPIC_SURVEY_DELETE;
import static com.backend.allreva.surveyJoin.command.domain.SurveyJoinEvent.TOPIC_SURVEY_JOIN;
import static com.backend.allreva.survey.command.domain.SurveySavedEvent.TOPIC_SURVEY_SAVE;

@Slf4j
@RequiredArgsConstructor
@Service
public class SurveyListener {

    private final SurveyDocumentRepository surveyDocumentRepository;
    private final ObjectMapper objectMapper;


    @Transactional
    @KafkaListener(
            topics = TOPIC_SURVEY_SAVE,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleSaveEvent(final String message) {
        SurveySavedEvent event = deserializeSavedEvent(message);
        SurveyDocument surveyDocument = event.to();
        surveyDocumentRepository.save(surveyDocument);
        log.info("수요조사 es 저장 성공: {}", event.getSurveyId());
    }

    @Transactional
    @KafkaListener(
            topics = TOPIC_SURVEY_DELETE,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleDeleteEvent(final String message) {
        SurveyDeletedEvent event = deserializeDeleteEvent(message);
        Long surveyId = event.getSurveyId();
        surveyDocumentRepository.deleteById(surveyId.toString());
        log.info("수요조사 삭제 성공: {}", event.getSurveyId());
    }

    @Transactional
    @KafkaListener(
            topics = TOPIC_SURVEY_JOIN,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleJoinEvent(final String message) {
        SurveyJoinEvent event = deserializeJoinEvent(message);
        Long surveyId = event.getSurveyId();
        SurveyDocument surveyDocument = surveyDocumentRepository.findById(surveyId.toString())
                .orElseThrow(NotFoundException::new);

        surveyDocument.updateParticipationCount(event.getParticipationCount());
        log.info("수요조사 Join 성공: {}", event.getParticipationCount());
    }


    private SurveySavedEvent deserializeSavedEvent(final String message) {
        try {
            return objectMapper.readValue(message, SurveySavedEvent.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingError();
        }
    }
    private SurveyDeletedEvent deserializeDeleteEvent(final String message) {
        try {
            return objectMapper.readValue(message, SurveyDeletedEvent.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingError();
        }
    }
    private SurveyJoinEvent deserializeJoinEvent(final String message) {
        try {
            return objectMapper.readValue(message, SurveyJoinEvent.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingError();
        }
    }
}
