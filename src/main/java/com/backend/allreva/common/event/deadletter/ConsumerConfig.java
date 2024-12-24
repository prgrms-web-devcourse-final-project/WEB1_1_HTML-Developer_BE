package com.backend.allreva.common.event.deadletter;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Configuration
public class ConsumerConfig {

    private static final String THREAD_NAME = "DeadLetterConsumerThread";

    private final DeadLetterConsumer deadLetterConsumer;
    private ExecutorService executorService;

    @PostConstruct
    public void startConsumer() {
        executorService = Executors.newSingleThreadExecutor(factory -> {
            Thread consumerThread = new Thread(factory);
            consumerThread.setName(THREAD_NAME);
            return consumerThread;
        });
        executorService.submit(deadLetterConsumer);
    }

    @PreDestroy
    public void shutDownConsumer() {
        executorService.shutdown();
    }
}
