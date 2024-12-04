package com.backend.allreva.survey.infra;

import com.backend.allreva.survey.command.domain.SurveyEvent;
import com.backend.allreva.survey.exception.SurveyEventPublishingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyProducer {
    private final KafkaTemplate<Long, SurveyEvent> kafkaTemplate;

    private static final String TOPIC = "survey-event";

    @TransactionalEventListener
    public void publishEvent(SurveyEvent event) {
        try {
            ProducerRecord<Long, SurveyEvent> record =
                    new ProducerRecord<>(TOPIC, event.getEventId(), event);

            kafkaTemplate.send(record)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("이벤트 발행 실패 - eventId: {}, error: {}",
                                    event.getEventId(), ex.getMessage());
                        }else{
                            log.info("이벤트 발행 성공 - topic: {}, partition: {}, offset: {}",
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });
        }catch (Exception e){
            throw new SurveyEventPublishingException();
        }
    }
}
