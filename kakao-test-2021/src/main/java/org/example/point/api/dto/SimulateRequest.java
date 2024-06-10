package org.example.point.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.point.model.Command;

import java.util.List;

@Getter
@AllArgsConstructor
public class SimulateRequest {
    private final List<Command> commands;
}
