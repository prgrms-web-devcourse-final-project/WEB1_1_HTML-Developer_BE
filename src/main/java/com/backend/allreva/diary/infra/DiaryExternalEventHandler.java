package com.backend.allreva.diary.infra;

import com.backend.allreva.common.event.EventRepository;
import com.backend.allreva.diary.command.domain.DiaryCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiaryExternalEventHandler {

    private static final String TOPIC = "diaryImage";
    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final EventRepository eventRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void send(@Payload DiaryCreatedEvent event) {
        log.info("DiaryImageSender 전송");
        kafkaTemplate.send(TOPIC, toPayload(event))
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {

                    }
                });
    }

    private String toPayload(DiaryCreatedEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }



}
