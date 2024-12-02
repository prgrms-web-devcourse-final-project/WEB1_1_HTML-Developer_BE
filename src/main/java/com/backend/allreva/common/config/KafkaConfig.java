package com.backend.allreva.common.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {

    @Bean
    public NewTopic ConcertLikeTopic() {
        return TopicBuilder.name("concertLike-event")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic rentTopic() {
        return TopicBuilder.name("rent-event")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic surveyTopic() {
        return TopicBuilder.name("survey-event")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
