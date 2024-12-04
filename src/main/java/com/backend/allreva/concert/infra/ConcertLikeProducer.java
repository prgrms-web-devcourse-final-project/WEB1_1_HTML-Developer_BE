package com.backend.allreva.concert.infra;

import com.backend.allreva.concert.exception.exception.EventPublishingException;
import com.backend.allreva.concert.command.domain.ConcertLikeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcertLikeProducer {
    private final KafkaTemplate<String, ConcertLikeEvent> kafkaTemplate;
    private static final String TOPIC = "concertLike-event";

    @TransactionalEventListener
    public void publishEvent(ConcertLikeEvent event) {
        try {
            ProducerRecord<String, ConcertLikeEvent> record =
                    new ProducerRecord<>(TOPIC, event.getEventId(), event);

            kafkaTemplate.send(record)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("이벤트 발행 실패 - eventId: {}, error: {}",
                                    event.getEventId(), ex.getMessage());
                        } else {
                            log.info("이벤트 발행 성공 - topic: {}, partition: {}, offset: {}",
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });
        } catch (Exception e) {
            throw new EventPublishingException();
        }
    }
}
