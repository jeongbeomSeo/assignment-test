package org.example.util;

import org.example.model.Location;

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
}
