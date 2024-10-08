package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoResponse {
    @JsonProperty("user_info")
    private List<UserInfoDTO> userInfo;     // Start API 에서 발급받은 auth_key
}
