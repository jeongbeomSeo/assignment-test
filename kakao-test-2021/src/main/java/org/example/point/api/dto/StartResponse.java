package org.example.point.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class StartResponse {
    @JsonProperty("auth_key")
    private String authKey;
    private Integer problem;
    private Integer time;
}
