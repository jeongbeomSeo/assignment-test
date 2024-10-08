package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WaitingLineResponse {
    @JsonProperty("waiting_line")
    private List<WaitingUserInfoDTO> waitingLine;       // Start API 에서 발급받은 auth_key
}
