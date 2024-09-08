package com.leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardDto {

    private List<User> userList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastModifyTimestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private int rank;
        private String nickname;
        private double score;
    }
}
