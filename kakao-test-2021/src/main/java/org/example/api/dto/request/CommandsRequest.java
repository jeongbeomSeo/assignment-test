package org.example.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.model.Commands;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class CommandsRequest {
    private List<Commands> commands;
}
