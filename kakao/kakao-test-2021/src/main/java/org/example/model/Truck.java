package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Truck {

    private Integer id;

    @Setter
    @JsonProperty("location_id")
    private Integer locationId;

    @Setter
    @JsonProperty("loaded_bikes_count")
    private Integer loadedBikesCount;
}
