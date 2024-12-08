package com.backend.allreva.survey_join.infra;

import com.backend.allreva.common.event.Event;
import com.backend.allreva.common.event.EventEntry;
import com.backend.allreva.common.event.EventRepository;
import com.backend.allreva.common.event.JsonParsingError;
import com.backend.allreva.survey_join.command.domain.SurveyJoinEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.backend.allreva.common.event.Topic.TOPIC_SURVEY_JOIN;

@Slf4j
@RequiredArgsConstructor
@Service
public class SurveyJoinInternalEventHandler {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

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
