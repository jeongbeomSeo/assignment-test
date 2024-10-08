package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GameResultResponse {
    @JsonProperty("game_result")
    private List<GameResultDTO> gameReulstList;
}
