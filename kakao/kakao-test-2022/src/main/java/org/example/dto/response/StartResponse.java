package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StartResponse {
    @JsonProperty("auth_key")
    private String authKey;     // Start API 를 통해 발급받은 key, 이후 문제 풀이에 진행되는 모든 API에 이 key를 사용
    private Integer problem;    // 선택한 시나리오 번호
    private Integer time;       // 현재 게임 서버에서의 시각 (0부터 시작)
}
