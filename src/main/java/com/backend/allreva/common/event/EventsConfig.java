package com.backend.allreva.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class EventsConfig {

    private final ApplicationEventPublisher publisher;

    @Bean
    public InitializingBean eventsInitializer() {
        return () -> Events.setPublisher(publisher);
    }
}
