package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Location {
    private int id;
    @JsonProperty("located_bikes_count")
    private int locatedBikesCount;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Location location = (Location) object;
        return id == location.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
