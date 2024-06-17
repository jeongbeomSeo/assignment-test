package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class Location {
    private Integer id;

    @Setter
    @JsonProperty("located_bikes_count")
    private Integer locatedBikesCount;
}
