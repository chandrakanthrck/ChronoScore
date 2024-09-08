package com.leaderboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_score") // Optional, ensures correct table naming
public class GameScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;           // Auto-generated ID for each score record
    private Long userId;       // ID of the user who earned the score
    private int score;         // The score earned by the user
    private LocalDateTime createdAt; // Timestamp when the score was earned

    // Constructor without the 'id' (for new records)
    public GameScore(Long userId, int score, LocalDateTime createdAt) {
        this.userId = userId;
        this.score = score;
        this.createdAt = createdAt;
    }
}
