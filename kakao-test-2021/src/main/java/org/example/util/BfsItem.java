package org.example.util;

import java.util.ArrayList;
import java.util.List;

public class BfsItem {
    boolean[] isVisited;
    int curPos;
    int loadedBikeCount;
    List<Integer> commandList;

    public BfsItem(boolean[] isVisited, int curPos, int loadedBikeCount, List<Integer> commandList) {
        this.isVisited = isVisited;
        this.curPos = curPos;
        this.loadedBikeCount = loadedBikeCount;
        this.commandList = commandList;
    }
}
