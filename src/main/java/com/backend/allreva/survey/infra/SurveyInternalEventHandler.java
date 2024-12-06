package com.backend.allreva.survey.infra;


import static com.backend.allreva.survey.command.domain.SurveyDeletedEvent.TOPIC_SURVEY_DELETE;
import static com.backend.allreva.survey.command.domain.SurveyJoinEvent.TOPIC_SURVEY_JOIN;
import static com.backend.allreva.survey.command.domain.SurveySavedEvent.TOPIC_SURVEY_SAVE;

import com.backend.allreva.common.event.Event;
import com.backend.allreva.common.event.EventEntry;
import com.backend.allreva.common.event.EventRepository;
import com.backend.allreva.common.event.JsonParsingError;
import com.backend.allreva.survey.command.domain.SurveyDeletedEvent;
import com.backend.allreva.survey.command.domain.SurveyJoinEvent;
import com.backend.allreva.survey.command.domain.SurveySavedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class SurveyInternalEventHandler {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onMessage(final SurveySavedEvent event) {
        String payload = serializeEvent(event);
        EventEntry eventEntry = EventEntry.builder()
                .topic(TOPIC_SURVEY_SAVE)
                .payload(payload)
                .build();

        eventRepository.save(eventEntry);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onMessage(final SurveyDeletedEvent event) {
        String payload = serializeEvent(event);
        EventEntry eventEntry = EventEntry.builder()
                .topic(TOPIC_SURVEY_DELETE)
                .payload(payload)
                .build();

        eventRepository.save(eventEntry);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onMessage(final SurveyJoinEvent event) {
        String payload = serializeEvent(event);
        EventEntry eventEntry = EventEntry.builder()
                .topic(TOPIC_SURVEY_JOIN)
                .payload(payload)
                .build();

        eventRepository.save(eventEntry);
    }


    private String serializeEvent(Event event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingError();
        }
    }
}
