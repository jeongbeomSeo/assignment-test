package org.example.point.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.point.model.Truck;

import java.util.List;

@Getter
@NoArgsConstructor
public class TrucksResponse {
    List<Truck> trucks;
}
