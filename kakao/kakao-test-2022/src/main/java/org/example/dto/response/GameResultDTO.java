package org.example.dto.response;

import lombok.Data;

@Data
public class GameResultDTO {
    private Integer win;        // 게임에서 이긴 유저 아이디
    private Integer lose;       // 게임에서 진 유저 아이디
    private Integer taken;      // 게임을 하는 데 걸린 시간
}
