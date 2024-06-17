package org.example.direct.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommandsResponse {
    private String status;
    private Integer timer;
    private Integer failedRequestsCount;
    private Float distance;
}
