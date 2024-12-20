package com.backend.allreva.common.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DeadLetterQueueConsumer implements Runnable {

    private final DeadLetterQueue deadLetterQueue;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Event event = deadLetterQueue.take();
                Events.raise(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void freeze() {
        try {
            Thread.sleep(10000); // 10초
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
