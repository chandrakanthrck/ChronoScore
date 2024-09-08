package com.leaderboard.config;

import com.leaderboard.dto.GameScoreDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    private final String bootstrapServers = "localhost:9092";

    // Producer configuration for GameScoreDto
    @Bean
    public Map<String, Object> gameScoreProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, GameScoreDto> gameScoreProducerFactory() {
        return new DefaultKafkaProducerFactory<>(gameScoreProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, GameScoreDto> gameScoreKafkaTemplate() {
        return new KafkaTemplate<>(gameScoreProducerFactory());
    }

    // Consumer configuration for GameScoreDto
    @Bean
    public Map<String, Object> gameScoreConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "redis_consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        // Do not include JsonDeserializer specific properties here
        return props;
    }

    @Bean
    public ConsumerFactory<String, GameScoreDto> gameScoreConsumerFactory() {
        JsonDeserializer<GameScoreDto> deserializer = new JsonDeserializer<>(GameScoreDto.class);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeHeaders(false); // Optionally, you can add this if you don't use type headers

        return new DefaultKafkaConsumerFactory<>(gameScoreConsumerConfigs(), new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GameScoreDto> gameScoreKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GameScoreDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(gameScoreConsumerFactory());
        return factory;
    }
}