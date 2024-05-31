package org.example.util;

import org.example.model.Truck;

import java.util.List;

public class UtilTruck {
    public static Truck findNearestTruck(List<Truck> truckList, int locationId, int[] remainTimes) {

        int minDist = Integer.MAX_VALUE;
        int idx = -1;

        for (int i = 0; i < truckList.size(); i++) {
            int dist = getDistance(locationId, truckList.get(i).getLocationId());
            if (remainTimes[truckList.get(i).getId()] < dist * 6 + 12) continue;
            if (minDist > dist) {
                minDist = dist;
                idx = i;
            }
        }

        if (idx == -1) return null;

        return truckList.get(idx);
    }

    public static int[] moveTruck(int curPos, int targetPos) {
        if (curPos > targetPos) {
            if (curPos / 5 > targetPos / 5) {
                return new int[]{4, curPos - 5};
            }
            else {
                return new int[]{3, curPos - 1};
            }
        } else {
            if (curPos / 5 < targetPos / 5) {
                return new int[]{2, curPos + 5};
            } else {
                return new int[]{1, curPos + 1};
            }
        }
    }
    private static int getDistance(int id1, int id2) {
        return Math.abs(id1 / 5 - id2 / 5) + Math.abs(id1 % 5 - id2 % 5);
    }
}
