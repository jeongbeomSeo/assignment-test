package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Commands {
    private Integer truckId;
    private List<Integer> command;
}
