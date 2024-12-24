package com.backend.allreva.common.event.deadletter;

import com.backend.allreva.common.event.Event;
import com.backend.allreva.common.event.Events;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeadLetterConsumer implements Runnable {

    private final DeadLetterQueue deadLetterQueue;
    private volatile boolean isPaused = false;

    private final Lock consumerLock = new ReentrantLock();
    private final Condition condition = consumerLock.newCondition();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (isPaused) {
                waitForProcess();
            }
            try {
                Event event = deadLetterQueue.take();
                Events.raise(event);
                log.info("consumer 에서 이벤트 발행: {}", event.getTimestamp());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("consumer 한테 왜 interrupt() 발생????");
            }
        }
    }

    private void waitForProcess() {
        try {
            consumerLock.lock();
            condition.await(10, TimeUnit.SECONDS); // 락 반납하고 10초동안 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            consumerLock.unlock();
            this.isPaused = false;
        }
    }

    public void pause() {
        this.isPaused = true;
    }
}
