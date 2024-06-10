package org.example.point.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class SimulateResponse {
    private String status;
    private Integer time;
    @JsonProperty("failed_requests_count")
    private Float failedRequestsCount;
    private Float distance;
}
