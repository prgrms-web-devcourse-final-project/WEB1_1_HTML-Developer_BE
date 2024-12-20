package com.backend.allreva.common.event;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


public class QueueStarter {

    private final Thread consumerThread;

    public QueueStarter(DeadLetterQueueConsumer consumer) {
        this.consumerThread = new Thread(consumer);
    }

    @PostConstruct
    private void startDeadLetterQueue() {
        consumerThread.start();
    }

    @PreDestroy
    private void terminateDeadLetterQueue() {
        consumerThread.interrupt();
    }
}
