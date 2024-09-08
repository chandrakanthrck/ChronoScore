package com.leaderboard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leaderboard.dto.GameScoreDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // Inject ObjectMapper from JacksonConfig
    @Bean
    public RedisTemplate<String, GameScoreDto> redisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, GameScoreDto> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);  // Use the RedisConnectionFactory directly

        // Set up serialization using the injected ObjectMapper
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        template.afterPropertiesSet();
        return template;
    }
}
