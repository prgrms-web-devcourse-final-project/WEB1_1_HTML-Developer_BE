package com.backend.allreva.common.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

import static com.backend.allreva.common.event.Topic.*;

@Configuration
@EnableKafka
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {

    // 토픽 설정
    @Bean
    public NewTopic concertLikeTopic() {
        return TopicBuilder.name(TOPIC_CONCERT_VIEW)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic rentSaveTopic() {
        return TopicBuilder.name(TOPIC_RENT_SAVE)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic rentDeleteTopic() {
        return TopicBuilder.name(TOPIC_RENT_DELETE)
                .partitions(1)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic surveySaveTopic() {
        return TopicBuilder.name(TOPIC_SURVEY_SAVE)
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic surveyDeleteTopic() {
        return TopicBuilder.name(TOPIC_SURVEY_DELETE)
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic surveyJoinTopic() {
        return TopicBuilder.name(TOPIC_SURVEY_JOIN)
                .partitions(1)
                .replicas(1)
                .build();
    }

   /* // Producer Factory 설정
    @Bean
    public ProducerFactory<Long, SurveyCreatedEvent> surveyProducerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        DefaultKafkaProducerFactory<Long, SurveyCreatedEvent> factory = new DefaultKafkaProducerFactory<>(config);
        factory.setValueSerializer(new JsonSerializer<>(objectMapper));
        return factory;
    }

    // KafkaTemplate 설정
    @Bean
    public KafkaTemplate<Long, SurveyCreatedEvent> surveyKafkaTemplate(
            ProducerFactory<Long, SurveyCreatedEvent> surveyProducerFactory) {
        return new KafkaTemplate<>(surveyProducerFactory);
    }


    @Bean
    public ProducerFactory<String, String> concertLikeProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> concertLikeKafkaTemplate() {
        return new KafkaTemplate<>(concertLikeProducerFactory());
    }


    @Bean
    public ConsumerFactory<Long, SurveyCreatedEvent> surveyConsumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<SurveyCreatedEvent> deserializer = new JsonDeserializer<>(SurveyCreatedEvent.class, objectMapper);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new LongDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, SurveyCreatedEvent> kafkaListenerContainerFactory(
            ConsumerFactory<Long, SurveyCreatedEvent> surveyConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Long, SurveyCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(surveyConsumerFactory);
        return factory;
    }*/
}