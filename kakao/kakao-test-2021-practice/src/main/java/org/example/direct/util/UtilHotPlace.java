package org.example.direct.util;

import org.example.direct.model.HotPlaceInfo;
import org.example.direct.model.Location;

import java.util.*;

public class UtilHotPlace {
    private static final List<HotPlaceInfo> hotPlaceInfoMemory = new ArrayList<>();

    public static void addHotPlaceInfo(HotPlaceInfo hotPlaceInfo) {
        for (int i = 0; i < hotPlaceInfoMemory.size(); i++) {
            if (hotPlaceInfoMemory.get(i).equals(hotPlaceInfo)) {
                // Duplicate Exception
                return;
            }
        }

        hotPlaceInfoMemory.add(hotPlaceInfo);
    }

    public static List<HotPlaceInfo> getHotPlaceInfo() {
        return hotPlaceInfoMemory;
    }

    public static List<Location> getLackBikeHotPlaceLocations(List<Location> locationList) {

        List<Location> lackBikeLocations = new ArrayList<>();

        for (HotPlaceInfo hotPlaceInfo : hotPlaceInfoMemory) {
            if (locationList.get(hotPlaceInfo.getRentalLocationId()).getLocatedBikesCount() == 0) {
                lackBikeLocations.add(locationList.get(hotPlaceInfo.getRentalLocationId()));
            }
        }

        return lackBikeLocations;
    }

    public static HotPlaceInfo getHotPlace(int rentalLocationId) {

        for (HotPlaceInfo hotPlaceInfo : hotPlaceInfoMemory) {
            if (hotPlaceInfo.getRentalLocationId() == rentalLocationId) {
                return hotPlaceInfo;
            }
        }

        return null;
    }
}
