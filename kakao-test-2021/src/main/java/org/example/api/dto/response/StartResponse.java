package org.example.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StartResponse {
    @JsonProperty("auth_key")
    private String authKey;
    private Integer problem;
    private Integer time;
}
