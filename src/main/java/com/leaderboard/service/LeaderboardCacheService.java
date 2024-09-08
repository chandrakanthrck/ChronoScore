package com.leaderboard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LeaderboardCacheService {

    private static final Logger logger = LoggerFactory.getLogger(LeaderboardCacheService.class);

    private static final String LEADERBOARD_KEY = "leaderboard";

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public LeaderboardCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Add or update a user's score in the leaderboard.
     * @param userId The ID of the user.
     * @param score The score to be added or updated.
     */
    public void updateUserScore(Long userId, double score) {
        redisTemplate.opsForZSet().incrementScore(LEADERBOARD_KEY, userId.toString(), score);
        logger.info("Updated score for userId={} with score={}", userId, score);
    }

    /**
     * Get the leaderboard with top N users.
     * @param topN The number of top users to retrieve.
     * @return Set of users with their scores in descending order.
     */
    public Set<ZSetOperations.TypedTuple<String>> getTopUsers(int topN) {
        Set<ZSetOperations.TypedTuple<String>> topUsers = redisTemplate.opsForZSet().reverseRangeWithScores(LEADERBOARD_KEY, 0, topN - 1);
        logger.info("Retrieved top {} users from leaderboard", topN);
        return topUsers;
    }

    /**
     * Get the rank of a specific user in the leaderboard.
     * @param userId The ID of the user.
     * @return The rank of the user, or null if the user is not in the leaderboard.
     */
    public Long getUserRank(Long userId) {
        Long rank = redisTemplate.opsForZSet().reverseRank(LEADERBOARD_KEY, userId.toString());
        if (rank == null) {
            logger.warn("User with ID {} is not in the leaderboard", userId);
        } else {
            logger.info("Retrieved rank for userId={}: {}", userId, rank);
        }
        return rank;
    }

    /**
     * Get the score of a specific user in the leaderboard.
     * @param userId The ID of the user.
     * @return The score of the user, or null if the user is not in the leaderboard.
     */
    public Double getUserScore(Long userId) {
        Double score = redisTemplate.opsForZSet().score(LEADERBOARD_KEY, userId.toString());
        if (score == null) {
            logger.warn("User with ID {} has no score in the leaderboard", userId);
        } else {
            logger.info("Retrieved score for userId={}: {}", userId, score);
        }
        return score;
    }

    /**
     * Remove a user from the leaderboard.
     * @param userId The ID of the user.
     */
    public void removeUser(Long userId) {
        redisTemplate.opsForZSet().remove(LEADERBOARD_KEY, userId.toString());
        logger.info("Removed userId={} from leaderboard", userId);
    }
}