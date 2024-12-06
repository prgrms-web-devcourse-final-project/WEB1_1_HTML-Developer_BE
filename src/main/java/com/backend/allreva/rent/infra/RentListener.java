package com.backend.allreva.rent.infra;

import com.backend.allreva.common.event.JsonParsingError;
import com.backend.allreva.rent.command.domain.RentDeleteEvent;
import com.backend.allreva.rent.command.domain.RentSaveEvent;
import com.backend.allreva.rent.query.application.domain.RentDocument;
import com.backend.allreva.rent.query.application.domain.RentDocumentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.backend.allreva.rent.command.domain.RentDeleteEvent.TOPIC_RENT_DELETE;
import static com.backend.allreva.rent.command.domain.RentSaveEvent.TOPIC_RENT_SAVE;

@Slf4j
@RequiredArgsConstructor
@Service
public class RentListener {

    private final RentDocumentRepository rentDocumentRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(
            topics = TOPIC_RENT_SAVE,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleSaveEvent(final String message) {
        RentSaveEvent event = deserializeSavedEvent(message);
        RentDocument rentDocument = event.to();
        rentDocumentRepository.save(rentDocument);
        log.info("차 대절 es 저장 성공: {}", rentDocument.getId());
    }

    @Transactional
    @KafkaListener(
            topics = TOPIC_RENT_DELETE,
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleDeleteEvent(final String message) {
        RentDeleteEvent event = deserializeDeleteEvent(message);
        Long rentId = event.getRentId();
        rentDocumentRepository.deleteById(rentId.toString());
        log.info("차 대절 삭제 성공: {}", rentId);
    }


    private RentSaveEvent deserializeSavedEvent(final String message) {
        try {
            return objectMapper.readValue(message, RentSaveEvent.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingError();
        }
    }
    private RentDeleteEvent deserializeDeleteEvent(final String message) {
        try {
            return objectMapper.readValue(message, RentDeleteEvent.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingError();
        }
    }
}
