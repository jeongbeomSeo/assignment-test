package org.example.point.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Command {
    @JsonProperty("truck_id")
    private Integer truckId;
    private List<Integer> command;
}
