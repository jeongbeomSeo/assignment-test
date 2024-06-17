package org.example.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.model.Location;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class LocationsResponseDTO {
    List<Location> locations;
}
