package org.example.point.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.point.model.Location;

import java.util.List;

@Getter
@NoArgsConstructor
public class LocationsResponse {
    private List<Location> locations;
}
