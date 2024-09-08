package com.leaderboard.consumer;

import com.leaderboard.dto.GameScoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class GameScoreRedisConsumer {

    @Autowired
    private RedisTemplate<String, GameScoreDto> redisTemplate;

    @KafkaListener(topics = "game_score", groupId = "redis_consumer", containerFactory = "gameScoreKafkaListenerContainerFactory")
    public void consume(GameScoreDto gameScore) {
        if (gameScore != null && gameScore.getUserId() != null) {
            redisTemplate.opsForValue().set(gameScore.getUserId().toString(), gameScore);
            System.out.println("Stored score: " + gameScore.getScore());
        } else {
            System.err.println("Received null or invalid GameScoreDto");
        }
    }
}