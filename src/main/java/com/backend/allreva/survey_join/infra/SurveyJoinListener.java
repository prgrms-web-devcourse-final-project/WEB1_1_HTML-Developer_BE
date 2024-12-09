package com.backend.allreva.survey_join.infra;

import com.backend.allreva.common.event.JsonParsingError;
import com.backend.allreva.common.exception.NotFoundException;
import com.backend.allreva.survey.infra.elasticsearch.SurveyDocument;
import com.backend.allreva.survey.infra.elasticsearch.SurveyDocumentRepository;
import com.backend.allreva.survey_join.command.domain.SurveyJoinEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.backend.allreva.common.event.Topic.TOPIC_SURVEY_JOIN;

@Slf4j
@RequiredArgsConstructor
@Service
public class SurveyJoinListener {

    private final SurveyDocumentRepository surveyDocumentRepository;
    private final ObjectMapper objectMapper;

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

    private SurveyJoinEvent deserializeJoinEvent(final String message) {
        try {
            return objectMapper.readValue(message, SurveyJoinEvent.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingError();
        }
    }
}
