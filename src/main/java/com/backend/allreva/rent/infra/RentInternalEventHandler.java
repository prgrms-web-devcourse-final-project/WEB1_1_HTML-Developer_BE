package com.backend.allreva.rent.infra;


import com.backend.allreva.common.event.Event;
import com.backend.allreva.common.event.EventEntry;
import com.backend.allreva.common.event.EventRepository;
import com.backend.allreva.common.event.JsonParsingError;
import com.backend.allreva.rent.command.domain.RentDeleteEvent;
import com.backend.allreva.rent.command.domain.RentSaveEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.backend.allreva.common.event.Topic.TOPIC_RENT_DELETE;
import static com.backend.allreva.common.event.Topic.TOPIC_RENT_SAVE;

@Slf4j
@RequiredArgsConstructor
@Service
public class RentInternalEventHandler {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onMessage(final RentSaveEvent event) {
        String payload = serializeEvent(event);
        EventEntry eventEntry = EventEntry.builder()
                .topic(TOPIC_RENT_SAVE)
                .payload(payload)
                .build();

        eventRepository.save(eventEntry);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onMessage(final RentDeleteEvent event) {
        String payload = serializeEvent(event);
        EventEntry eventEntry = EventEntry.builder()
                .topic(TOPIC_RENT_DELETE)
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
