package org.example.point.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Truck {
    private Integer id;
    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("loaded_bikes_count")
    private Integer loadedBikesCount;
}
