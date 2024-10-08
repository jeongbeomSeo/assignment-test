package org.example.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MatchInfoDTO {
    private List<int[]> pairs;

    public MatchInfoDTO() {
        this.pairs = new ArrayList<>();
    }

    public void addPair(int a, int b) {
        pairs.add(new int[]{a, b});
    }
}
