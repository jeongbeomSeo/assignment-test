package org.example.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.model.Truck;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TruckResponse {
    private List<Truck> trucks;
}
