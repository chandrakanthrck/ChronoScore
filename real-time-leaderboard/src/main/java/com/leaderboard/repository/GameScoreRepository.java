package com.leaderboard.repository;


import com.leaderboard.dto.GameScoreDto;
import com.leaderboard.entity.GameScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {

    // Custom query to find scores by userId
    List<GameScore> findByUserId(Long userId);

    // Custom query to find top 10 users by score (sum)
    // You could implement custom queries using @Query annotations if needed
}

