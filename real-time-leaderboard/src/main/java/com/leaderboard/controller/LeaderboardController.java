package com.leaderboard.controller;

import com.leaderboard.entity.LeaderboardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final List<LeaderboardData> leaderboard = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("/updateLeaderboard")
    public void updateLeaderboard(@RequestBody LeaderboardData data) {
        synchronized (leaderboard) {
            leaderboard.removeIf(ld -> ld.getPlayerName().equals(data.getPlayerName())); // Remove old data if exists
            leaderboard.add(data); // Add new data
        }
        // Publish the updated leaderboard data to the topic
        template.convertAndSend("/topic/leaderboard", leaderboard);
    }

    @GetMapping
    public List<LeaderboardData> getLeaderboard() {
        synchronized (leaderboard) {
            // Sort leaderboard by score in descending order
            leaderboard.sort(Comparator.comparingInt(LeaderboardData::getScore).reversed());
            return new ArrayList<>(leaderboard);
        }
    }

    @PostMapping("/add")
    public String addGameScore(@RequestParam String playerName, @RequestParam int score) {
        try {
            // Create LeaderboardData
            LeaderboardData leaderboardData = new LeaderboardData();
            leaderboardData.setPlayerName(playerName);
            leaderboardData.setScore(score);

            // Update in-memory leaderboard data
            synchronized (leaderboard) {
                leaderboard.removeIf(ld -> ld.getPlayerName().equals(playerName)); // Remove old data if exists
                leaderboard.add(leaderboardData); // Add new data
            }

            // Publish the updated leaderboard data to the topic
            template.convertAndSend("/topic/leaderboard", leaderboard);

            return "Score added successfully!";
        } catch (Exception e) {
            // Handle exceptions (e.g., log the error)
            return "An error occurred while adding the score.";
        }
    }
}