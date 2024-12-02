package com.backend.allreva.common.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Events {

    private static ApplicationEventPublisher publisher;

    private Events() {
    }

    static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Event event) {
        if (publisher != null) {
            publisher.publishEvent(event);
        }
    }
}
