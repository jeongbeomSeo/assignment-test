package org.example.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PlaneMoveResult {
    private List<Integer> command;
    private int pos;
}
