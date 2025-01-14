package com.backend.allreva.common.event.deadletter;

import com.backend.allreva.common.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
public class DeadLetterQueue {

    private final BlockingQueue<Event> queue = new LinkedBlockingQueue<>();

    protected void put(final Event deadLetter) {
        try {
            deadLetter.markAsReissued();
            queue.put(deadLetter);
        } catch (InterruptedException e) {
            log.warn("DLQ put() 에서 인터럽트 발생");
            Thread.currentThread().interrupt();
        }
    }

    protected Event take() throws InterruptedException {
        return queue.take();
    }

}
