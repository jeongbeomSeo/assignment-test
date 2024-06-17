package org.example.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.point.model.Point;

@Getter
@AllArgsConstructor
public class TruckMoveDTO {
    private final Point point;
    private final int direction;
}
