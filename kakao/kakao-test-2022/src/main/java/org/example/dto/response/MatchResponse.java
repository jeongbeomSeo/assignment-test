package org.example.dto.response;

import lombok.Data;

@Data
public class MatchResponse {
    private String status;      // 현재 카카오 게임 서버의 상태
    private String time;        // 현재 시각(턴) (쵸엉 시각에서 1분 경과한 시각)
}
