package com.backend.allreva.common.config;

import com.backend.allreva.concert.command.domain.ConcertLikeEvent;
import com.backend.allreva.survey.command.domain.SurveyEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {

    // 토픽 설정
    @Bean
    public NewTopic concertLikeTopic() {
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

    // Producer Factory 설정
    @Bean
    public ProducerFactory<Long, SurveyEvent> surveyProducerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        DefaultKafkaProducerFactory<Long, SurveyEvent> factory = new DefaultKafkaProducerFactory<>(config);
        factory.setValueSerializer(new JsonSerializer<>(objectMapper));
        return factory;
    }

    @Bean
    public ProducerFactory<String, ConcertLikeEvent> concertLikeProducerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        DefaultKafkaProducerFactory<String, ConcertLikeEvent> factory = new DefaultKafkaProducerFactory<>(config);
        factory.setValueSerializer(new JsonSerializer<>(objectMapper));
        return factory;
    }

    // KafkaTemplate 설정
    @Bean
    public KafkaTemplate<Long, SurveyEvent> surveyKafkaTemplate(
            ProducerFactory<Long, SurveyEvent> surveyProducerFactory) {
        return new KafkaTemplate<>(surveyProducerFactory);
    }

    @Bean
    public KafkaTemplate<String, ConcertLikeEvent> concertLikeKafkaTemplate(
            ProducerFactory<String, ConcertLikeEvent> concertLikeProducerFactory
    ) {
        return new KafkaTemplate<>(concertLikeProducerFactory);
    }

    @Bean
    public ConsumerFactory<Long, SurveyEvent> surveyConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<SurveyEvent> deserializer = new JsonDeserializer<>(SurveyEvent.class, objectMapper);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new LongDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, SurveyEvent> kafkaListenerContainerFactory(
            ConsumerFactory<Long, SurveyEvent> surveyConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, SurveyEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(surveyConsumerFactory);
        return factory;
    }
}