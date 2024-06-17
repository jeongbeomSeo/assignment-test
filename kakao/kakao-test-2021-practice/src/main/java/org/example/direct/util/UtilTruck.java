package org.example.direct.util;

import org.example.direct.model.Commands;
import org.example.direct.model.Location;
import org.example.direct.model.Truck;

import java.util.List;

public class UtilTruck {
    /**
     * 해당 위치로 이동 가능한 트럭이 없을 경우 null return
     */
    public static Truck findNearestTruck(List<Truck> truckList, int locationId, int[] remainTimes) {

        int minValue = Integer.MAX_VALUE;
        int idx = -1;

        for (int i = 0; i < truckList.size(); i++) {
            int dist = getDistance(locationId, truckList.get(i).getLocationId());
            int value = dist * 6 + 12 - remainTimes[truckList.get(i).getId()];
            if (minValue > value) {
                minValue = value;
                idx = i;
            }
        }

        return truckList.get(idx);
    }

    public static Truck findNearestTruckRange(List<Truck> truckList, int locationId, int[] remainTimes, int start, int end) {

        int minValue = Integer.MAX_VALUE;
        int idx = -1;

        for (int i = start; i < end; i++) {
            int dist = getDistance(locationId, truckList.get(i).getLocationId());
            if (remainTimes[truckList.get(i).getId()] == 0) continue;
            int value = dist * 6 + 12 - remainTimes[truckList.get(i).getId()];
            if (minValue > value) {
                minValue = value;
                idx = i;
            }
        }

        if (idx == -1) return null;
        return truckList.get(idx);
    }

    public static boolean canGoTargetLocation(Truck truck, int[] times, int locationId) {
        int dist = getDistance(locationId, truck.getLocationId());
        return times[truck.getId()] < dist * 6 + 12;
    }
    public static void loadedBike(Location location, int[] remainTimes, Truck truck, List<Commands> commandsList) {
        int initBikeCount = location.getLocatedBikesCount();
        while (remainTimes[truck.getId()] != 0) {
            if (truck.getLoadedBikesCount() >= 5 || location.getLocatedBikesCount() - 1 < (initBikeCount + 1) / 2) break;

            truck.setLoadedBikesCount(truck.getLoadedBikesCount() + 1);
            location.setLocatedBikesCount(location.getLocatedBikesCount() - 1);
            commandsList.get(truck.getId()).getCommand().add(5);

            remainTimes[truck.getId()] -= 6;
        }
    }
    public static void unloadBike(Location location, int[] remainTimes, Truck truck, List<Commands> commandsList) {
        while (remainTimes[truck.getId()] != 0) {
            if (truck.getLoadedBikesCount() == 0 || location.getLocatedBikesCount() >= 3) break;

            truck.setLoadedBikesCount(truck.getLoadedBikesCount() - 1);
            location.setLocatedBikesCount(location.getLocatedBikesCount() + 1);
            commandsList.get(truck.getId()).getCommand().add(6);

            remainTimes[truck.getId()] -= 6;
        }
    }
    public static void moveTargetPoint(int targetPos, int[] remainTimes, Truck truck, List<Commands> commandsList) {
        while (remainTimes[truck.getId()] != 0) {
            int curPos = truck.getLocationId();
            /**
             * ele1: 방향(direction)
             * ele2: 도착한 위치의 location ID
             */
            int[] moveResult = move(curPos, targetPos);
            commandsList.get(truck.getId()).getCommand().add(moveResult[0]);
            truck.setLocationId(moveResult[1]);

            remainTimes[truck.getId()] -= 6;

            // 목표 지점에 도착을 하였다면 멈추기
            if (moveResult[1] == targetPos) break;
        }
    }

    private static int[] move(int curPos, int targetPos) {
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
