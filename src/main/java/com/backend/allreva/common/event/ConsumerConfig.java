package com.backend.allreva.common.event;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Configuration
public class ConsumerConfig {

    private static final String THREAD_NAME = "DeadLetterConsumer";

    private final DeadLetterConsumer deadLetterConsumer;
    private ExecutorService executorService;

    @PostConstruct
    public void startConsumer() {

        Thread consumerThread = new Thread(deadLetterConsumer);
        consumerThread.setName(THREAD_NAME);
        consumerThread.start();
    }

    @PreDestroy
    public void shutDownConsumer() {

    }
}
