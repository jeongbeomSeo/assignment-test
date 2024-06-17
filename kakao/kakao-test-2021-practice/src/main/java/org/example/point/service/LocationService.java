package org.example.point.service;

import org.example.point.model.Location;
import org.example.point.model.Point;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class LocationService {

    public void updateLocatedBike(Location location, int bikeCount) {
        location.setLocatedBikesCount(bikeCount);
    }

    public static Location getNearestLocationHavedBike(Integer problem, List<Location> locations, int targetLocation) {

        int[] dr = new int[]{1, 0, -1, 0};
        int[] dc = new int[]{0, 1, 0, -1};

        Point initPoint = PointerService.convertIdToPoint(problem, targetLocation);

        Queue<Point> q = new ArrayDeque<>();
        boolean[][] isVisited = new boolean[3600][3600];
        q.add(initPoint);
        isVisited[initPoint.row][initPoint.col] = true;

        while (!q.isEmpty()) {
            Point curPoint = q.poll();

            Location location = locations.get(PointerService.convertPointToId(problem, curPoint));
            if (!initPoint.equals(curPoint) && location.getLocatedBikesCount() >= 1) {
                return location;
            }

            for (int i = 0; i < 4; i++) {
                int nxtRow = curPoint.row + dr[i];
                int nxtCol = curPoint.col + dc[i];

                if (isValidPoint(problem, nxtRow, nxtCol) && !isVisited[nxtRow][nxtCol]) {
                    q.add(new Point(nxtRow, nxtCol));
                    isVisited[nxtRow][nxtCol] = true;
                }
            }
        }
        return null;
    }
    private static boolean isValidPoint(Integer prblem, int row, int col) {
        if (prblem == 1) {
            return row >= 0 && col >= 0 && row < 5 && col < 5;
        } else {
            return row >= 0 && col >= 0 && row < 3600 && col < 3600;
        }
    }
}
