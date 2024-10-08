package org.example.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ChangeGradeRequest {
    private List<ChangeGradeDTO> commands;

    public ChangeGradeRequest(List<ChangeGradeDTO> commands) {
        this.commands = commands;
    }
}
