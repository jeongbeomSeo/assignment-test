package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScoreResponse {

    private String status;      // 서버 상태
    @JsonProperty("efficiency_score")
    private Double efficiencyScore;     // 효율성 점수
    @JsonProperty("accuracy_score1")
    private Double accuracyScore1;      // 정확성 점수 1
    @JsonProperty("accuracy_score2")
    private Double accuracyScore2;      // 정확성 점수 1
    private Double score;       // 총점
}
