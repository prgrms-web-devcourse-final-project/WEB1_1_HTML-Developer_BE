package com.backend.allreva.diary.infra;

import com.backend.allreva.common.event.EventEntry;
import com.backend.allreva.common.event.EventRepository;
import com.backend.allreva.common.exception.PayloadConvertException;
import com.backend.allreva.diary.command.domain.DiaryCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
public class DiaryInternalEventHandler {

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handle(DiaryCreatedEvent event) {
        EventEntry eventEntry = EventEntry.builder()
                .type(event.getClass().getName())
                .payload(toJson(event))
                .build();
        eventRepository.save(eventEntry);
    }

    private String toJson(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new PayloadConvertException(e.getMessage());
        }
    }
}
