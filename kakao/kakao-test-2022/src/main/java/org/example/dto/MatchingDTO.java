package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchingDTO {
    private Integer id;
    private Integer grade;
    private Integer from;
}