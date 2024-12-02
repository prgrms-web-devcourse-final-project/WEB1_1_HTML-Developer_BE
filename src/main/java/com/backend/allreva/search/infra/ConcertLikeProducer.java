package com.backend.allreva.search.infra;

import com.backend.allreva.search.query.domain.ConcertLikeEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcertLikeProducer {
    private final KafkaTemplate<String, ConcertLikeEvent> kafkaTemplate;
    private static final String TOPIC = "concertLike-event";

    @Transactional
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
            log.error("이벤트 발행 중 예외 발생 - eventId: {}, error: {}",
                    event.getEventId(), e.getMessage());
        }
    }
}
