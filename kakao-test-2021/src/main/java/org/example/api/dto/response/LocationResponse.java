package org.example.api.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.model.Location;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LocationResponse {
    private List<Location> locations;
}
