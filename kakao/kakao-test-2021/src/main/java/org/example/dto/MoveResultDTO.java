package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MoveResultDTO {
    private Integer nxtLocationId;
    private Integer command;
}
