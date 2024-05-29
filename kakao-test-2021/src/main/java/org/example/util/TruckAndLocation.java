package org.example.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.model.Location;
import org.example.model.Truck;

@Getter
@AllArgsConstructor
public class TruckAndLocation {
    private Location location;
    private Truck truck;
}
