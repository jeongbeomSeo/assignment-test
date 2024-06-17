package org.example.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.model.Command;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class SimulateRequestDTO {
    private List<Command> commands;
}
