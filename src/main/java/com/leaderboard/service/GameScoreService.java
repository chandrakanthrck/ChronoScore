package com.leaderboard.service;

import com.leaderboard.entity.GameScore;
import com.leaderboard.repository.GameScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameScoreService {

    @Autowired
    private GameScoreRepository gameScoreRepository;

    public void saveGameScore(Long userId, int score) {
        GameScore gameScore = new GameScore(null, userId, score, LocalDateTime.now());
        gameScoreRepository.save(gameScore);
    }
}
