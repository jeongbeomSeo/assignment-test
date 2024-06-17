package org.example.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StartResponseDTO {
    @JsonProperty("auth_key")
    String authKey;

    Integer problem;

    Integer time;
}
