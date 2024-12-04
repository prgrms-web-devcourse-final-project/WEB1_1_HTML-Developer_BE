package com.backend.allreva.common.config;

import com.backend.allreva.concert.command.domain.ConcertLikeEvent;
import com.backend.allreva.survey.command.domain.SurveyEvent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

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

    @Bean
    public ObjectMapper KafkaobjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }


    @Bean
    public KafkaTemplate<Long, SurveyEvent> SurveykafkaTemplate(
            ProducerFactory<Long, SurveyEvent> producerFactory,
            ObjectMapper objectMapper) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, ConcertLikeEvent> ConcertLikekafkaTemplate(
            ProducerFactory<String, ConcertLikeEvent> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
