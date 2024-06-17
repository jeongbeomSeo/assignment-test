package org.example.point.register;

import lombok.Getter;
import lombok.Setter;

/**
 * 상차, 하차가 아닌 Truck이 움직이는 것에 대한 기록
 */
@Getter
public class TruckInfo {
    private final int truckId;
    private final int upperRow;
    private final int lowerRow;
    private final int leftCol;
    private final int rightCol;
    private final boolean[][] isVisited;
    @Setter
    private int direction;
    @Setter
    private boolean isTracking;
    @Setter
    // HotPlace 차량
    private boolean isLoading;

    public TruckInfo(int truckId, int upperRow, int lowerRow, int leftCol, int rightCol, Integer problem) {
        this.truckId = truckId;
        this.upperRow = upperRow;
        this.lowerRow = lowerRow;
        this.leftCol = leftCol;
        this.rightCol = rightCol;
        this.isVisited = initIsVisited(problem);
        this.direction = 1;
        this.isTracking = false;
    }
    private boolean[][] initIsVisited(Integer problem) {
        boolean[][] isVisited;
        if (problem == 1) {
            isVisited = new boolean[5][5];
        } else {
            isVisited = new boolean[60][60];
        }
        return isVisited;
    }
}
