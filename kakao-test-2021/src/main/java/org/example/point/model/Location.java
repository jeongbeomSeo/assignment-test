package org.example.point.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Location {
    private Integer id;
    @JsonProperty("located_bikes_count")
    private Integer locatedBikesCount;
}
