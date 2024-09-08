package com.leaderboard.consumer;

import com.leaderboard.dto.GameScoreDto;
import com.leaderboard.entity.GameScore;
import com.leaderboard.repository.GameScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GameScorePgConsumer {

    private static final Logger logger = LoggerFactory.getLogger(GameScorePgConsumer.class);

    @Autowired
    private GameScoreRepository gameScoreRepository;

    @KafkaListener(topics = "game_score", groupId = "pg_consumer", containerFactory = "gameScoreKafkaListenerContainerFactory")
    public void consume(GameScoreDto gameScoreDto) {
        if (gameScoreDto != null && gameScoreDto.getUserId() != null) {
            GameScore gameScore = new GameScore(
                    gameScoreDto.getUserId(),
                    gameScoreDto.getScore(),
                    gameScoreDto.getCreatedAt()
            );
            gameScoreRepository.save(gameScore);
            logger.info("Game score saved to PostgreSQL: UserId={}, Score={}", gameScoreDto.getUserId(), gameScoreDto.getScore());
        } else {
            logger.error("Received null or invalid GameScoreDto: {}", gameScoreDto);
        }
    }
}
