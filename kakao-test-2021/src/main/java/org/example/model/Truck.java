package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Truck truck = (Truck) object;
        return Objects.equals(id, truck.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

