package org.example.service;

import org.example.model.Location;

import java.util.List;

public class LocationService {

    public static Location getLocationHavingLowestBike(List<Location> locations) {

        Location result = null;
        int lowestBikeCount = Integer.MAX_VALUE;
        for (int i = 0; i < locations.size(); i++) {
            Location curLocation = locations.get(i);
            if (lowestBikeCount > curLocation.getLocatedBikesCount()) {
                result = curLocation;
                lowestBikeCount = curLocation.getLocatedBikesCount();
            }
        }
        return result;
    }

    public static Location getLocationHavingMostBike(List<Location> locations) {

        Location result = null;
        int mostBikeCount = Integer.MIN_VALUE;
        for (int i = 0; i < locations.size(); i++) {
            Location curLocation = locations.get(i);
            if (mostBikeCount < curLocation.getLocatedBikesCount()) {
                result = curLocation;
                mostBikeCount = curLocation.getLocatedBikesCount();
            }
        }
        return result;
    }
}
