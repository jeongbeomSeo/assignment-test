package org.example.util;

import org.example.model.Location;

import java.util.ArrayList;
import java.util.List;

public class UtilLocation {

    public static Location leastLocation(List<Location> locationList) {
        int leastIdx = 0;

        for (int i = 1; i < locationList.size(); i++) {
            if (locationList.get(leastIdx).getLocatedBikesCount() > locationList.get(i).getLocatedBikesCount()) {
                leastIdx = i;
            }
        }

        return locationList.get(leastIdx);
    }

    public static Location mostLocation(List<Location> locationList) {
        int mostIdx = 0;

        for (int i = 1; i < locationList.size(); i++) {
            if (locationList.get(mostIdx).getLocatedBikesCount() < locationList.get(i).getLocatedBikesCount()) {
                mostIdx = i;
            }
        }

        return locationList.get(mostIdx);
    }
    public static List<Location> mostLocationsByNearestLocation(List<Location> locationList, int targetLocationId) {

        List<Integer> nearestLocationIdxList = getNearestLocationIdxList(targetLocationId);

        List<Location> result = new ArrayList<>();
        // 갯수가 2개 보다 많은 지역이 있을 경우에만 이동
        for (Integer idx : nearestLocationIdxList) {
            if (locationList.get(idx).getLocatedBikesCount() >= 2) {
                result.add(locationList.get(idx));
            }
        }

        return result;
    }

    public static Location mostLocationByNearestLocation(List<Location> locationList, int targetLocationId) {

        List<Integer> nearestLocationIdxList = getNearestLocationIdxList(targetLocationId);
        int mostIdx = -1;
        int mostCount = 1;

        // 갯수가 1개 보다 많은 지역이 있을 경우에만 이동
        for (Integer idx : nearestLocationIdxList) {
            if (mostCount < locationList.get(idx).getLocatedBikesCount()) {
                mostIdx = idx;
                mostCount = locationList.get(idx).getLocatedBikesCount();
            }
        }

        if (mostIdx == -1) return null;

        return locationList.get(mostIdx);
    }
    private static List<Integer> getNearestLocationIdxList(int locationId) {
        List<Integer> nearestLocationIdxList = new ArrayList<>();
        if (locationId > 5) nearestLocationIdxList.add(locationId - 5);
        if (locationId > 0) nearestLocationIdxList.add(locationId - 1);
        if (locationId < 24) nearestLocationIdxList.add(locationId + 1);
        if (locationId < 20) nearestLocationIdxList.add(locationId + 5);

        return nearestLocationIdxList;
    }
}
