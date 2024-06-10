package org.example.point.service;

import org.example.point.dto.TruckMoveDTO;
import org.example.point.model.Point;
import org.example.point.model.Truck;

import java.util.List;

public class TruckService {
    private static final int[] DR = {0, 1, 0, -1, 0};
    private static final int[] DC = {0, 0, 1, 0, -1};

    public static Truck getNearestTruckHavingBike(Integer problem, List<Truck> truckList, int targetLocationId) {
        int idx = 0;
        int dist = getDistance(problem, truckList.get(idx).getLocationId(), targetLocationId);

        for (int i = 1; i < truckList.size(); i++) {
            if (truckList.get(i).getLoadedBikesCount() == 0) continue;
            int curDist = getDistance(problem, truckList.get(i).getLocationId(), targetLocationId);
            if (dist > curDist) {
                dist = curDist;
                idx = i;
            }
        }
        return truckList.get(idx);
    }

    public static TruckMoveDTO move(boolean[][] isVisited, int row, int col, int curDirection, int upperRow, int lowerRow, int leftRol, int rightCol) {
        int nxtRow = row + DR[curDirection];
        int nxtCol = col + DC[curDirection];
        int nxtDirection = curDirection;
        if (!PointerService.isValidPoint(nxtRow, nxtCol, upperRow, lowerRow, leftRol, rightCol) ||
                isVisited[nxtRow][nxtCol]) {
            nxtDirection = curDirection != 4 ? curDirection + 1: 1;
            nxtRow = row + DR[nxtDirection];
            nxtCol = col + DC[nxtDirection];
        }
        return new TruckMoveDTO(new Point(nxtRow, nxtCol), nxtDirection);
    }

    public static TruckMoveDTO moveTargetPoint(int row, int col, int targetRow, int targetCol) {
        if (row > targetRow) return new TruckMoveDTO(new Point(row - 1, col), 3);
        else if (row < targetRow) return new TruckMoveDTO(new Point(row + 1, col), 1);
        else if (col > targetCol) return new TruckMoveDTO(new Point(row, col - 1), 4);
        else if (col < targetCol) return new TruckMoveDTO(new Point(row, col + 1), 2);

        return null;
    }

    public static int getDistance(Integer problem, Integer locaiontId1, Integer locationId2) {
        if (problem == 1) {
            return Math.abs(locaiontId1 / 5 - locationId2 / 5) + Math.abs(locaiontId1 % 5 - locationId2 % 5);
        } else {
            return Math.abs(locaiontId1 / 60 - locationId2 / 60) + Math.abs(locaiontId1 % 60 - locationId2 % 60);
        }
    }
}
