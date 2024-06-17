package org.example.direct.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SimulateResponse {
    private String status;
    private Integer time;
    @JsonProperty("failed_requests_count")
    private Float failedRequestsCount;
    private Float distance;
}
