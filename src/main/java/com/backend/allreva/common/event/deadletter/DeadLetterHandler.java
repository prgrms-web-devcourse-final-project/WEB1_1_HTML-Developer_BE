package com.backend.allreva.common.event.deadletter;

import com.backend.allreva.common.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeadLetterHandler {

    private final DeadLetterQueue deadLetterQueue;
    private final DeadLetterConsumer deadLetterConsumer;

    public void put(Event deadLetter) {
        deadLetterConsumer.pause();
        deadLetterQueue.put(deadLetter);
    }
}
