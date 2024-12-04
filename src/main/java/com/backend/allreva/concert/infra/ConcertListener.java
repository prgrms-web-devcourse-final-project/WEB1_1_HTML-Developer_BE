package com.backend.allreva.concert.infra;

import com.backend.allreva.common.event.JsonParsingError;
import com.backend.allreva.concert.command.domain.ViewAddedEvent;
import com.backend.allreva.concert.query.application.domain.ConcertDocument;
import com.backend.allreva.concert.query.application.domain.ConcertSearchRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConcertListener {

    private final ConcertSearchRepository concertSearchRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(
            topics = "concertLike-event",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleConcertListEvent(String message) {
        ViewAddedEvent event = deserializeEvent(message);

        ConcertDocument concertDocument = concertSearchRepository.findByConcertCode(event.getConcertCode())
                .orElseThrow();
        concertDocument.updateViewCount(concertDocument.getViewCount());
        log.info("이벤트 수신, 조회수: {}", event.getViewCount());
    }

    private ViewAddedEvent deserializeEvent(String message) {
        try {
            return objectMapper.readValue(message, ViewAddedEvent.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingError();
        }
    }
}
