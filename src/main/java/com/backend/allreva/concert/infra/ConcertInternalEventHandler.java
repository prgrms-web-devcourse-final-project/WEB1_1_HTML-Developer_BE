package com.backend.allreva.concert.infra;

import com.backend.allreva.common.event.EventEntry;
import com.backend.allreva.common.event.EventRepository;
import com.backend.allreva.common.event.JsonParsingError;
import com.backend.allreva.concert.command.domain.ViewAddedEvent;
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
public class ConcertInternalEventHandler {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onMessage(ViewAddedEvent event) {
        String payload = serializeEvent(event);
        EventEntry eventEntry = EventEntry.builder()
                .topic(event.getTopic())
                .payload(payload)
                .build();

        eventRepository.save(eventEntry);
    }

    private String serializeEvent(ViewAddedEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingError();
        }
    }
}
