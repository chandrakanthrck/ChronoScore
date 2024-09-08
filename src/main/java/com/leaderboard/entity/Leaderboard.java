package com.leaderboard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Leaderboard {

    private List<UserScore> topScores;  // List of top user scores
    private LocalDateTime lastModifiedTimestamp; // Timestamp when the leaderboard was last updated
}
