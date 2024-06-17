package org.example.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.model.Truck;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class TrucksResponseDTO {
    List<Truck> trucks;
}
