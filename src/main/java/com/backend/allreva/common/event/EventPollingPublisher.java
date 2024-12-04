package com.backend.allreva.common.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventPollingPublisher {

    private static final int FIVE_SECONDS = 5000;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final EventRepository eventRepository;

    /**
     * 발행되지 않은 모든 이벤트 재발행
     * 예외 발생 시 발행 중단
     */
    @Scheduled(fixedDelay = FIVE_SECONDS) // 5초
    public void processRemains() {
        List<EventEntry> remains = eventRepository.findAllRemainEvents();

        List<Long> publishedEventIds = sendEventToKafka(remains);
        eventRepository.deleteAllById(publishedEventIds);
    }


    private List<Long> sendEventToKafka(List<EventEntry> remains) {
        List<Long> publishedEventIds = new ArrayList<>();

        for (EventEntry event : remains) {
            try {
                if (event.getTopic() == null) {
                    continue;
                }
                kafkaTemplate.send(event.getTopic(), event.getId().toString(), event.getPayload())
                        .get();
                publishedEventIds.add(event.getId());
            } catch (InterruptedException | ExecutionException e ) {
                handleException(e);
                break;
            }
        }
        return publishedEventIds;
    }

    private void handleException(Exception e) {
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        log.error(e.getMessage());
    }

}

