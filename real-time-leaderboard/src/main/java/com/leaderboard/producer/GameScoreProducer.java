package com.leaderboard.producer;

import com.leaderboard.dto.GameScoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GameScoreProducer {

    @Autowired
    private KafkaTemplate<String, GameScoreDto> kafkaTemplate;

    @Value("${kafka.topic.game_score}")
    private String gameScoreTopic;

    public void sendScore(GameScoreDto gameScore) {
        this.kafkaTemplate.send(gameScoreTopic, gameScore.getUserId().toString(), gameScore);
    }
}