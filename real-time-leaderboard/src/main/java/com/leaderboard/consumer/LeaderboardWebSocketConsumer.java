package com.leaderboard.consumer;

import com.leaderboard.entity.Leaderboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LeaderboardWebSocketConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LeaderboardWebSocketConsumer.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "leaderboard_change", groupId = "ws_consumer")
    public void broadcastLeaderboard(Leaderboard leaderboard) {
        if (leaderboard != null) {
            messagingTemplate.convertAndSend("/topic/leaderboard", leaderboard);
            logger.info("Broadcasting leaderboard update to WebSocket: {}", leaderboard);
        } else {
            logger.error("Received null Leaderboard data from Kafka.");
        }
    }
}