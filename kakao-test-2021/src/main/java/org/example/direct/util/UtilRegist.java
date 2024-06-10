package org.example.direct.util;

import org.example.direct.model.Location;
import org.example.direct.model.Regist;

import java.util.ArrayList;
import java.util.List;

public class UtilRegist {
    private static List<Regist> registList = new ArrayList<>();
    public static void addNewRegist(int locationId, int initBikeCount) {
        registList.add(new Regist(locationId, initBikeCount, 0));
    }

    public static void resetTotalRentedBikeCount() {
        for (Regist regist : registList) {
            regist.setTotalRentedBikeCount(0);
        }
    }

    public static void updateRegist(List<Location> locationList) {
        for (int i = 0; i < registList.size(); i++) {
            Regist regist = registList.get(i);

            int rentCount = regist.getCurBikeCount() - locationList.get(regist.getLocationId()).getLocatedBikesCount();
            if (rentCount > 0) {
                regist.setTotalRentedBikeCount(regist.getCurBikeCount() - locationList.get(regist.getLocationId()).getLocatedBikesCount());
            }
            regist.setCurBikeCount(locationList.get(regist.getLocationId()).getLocatedBikesCount());
        }
    }

    public static Regist getRegist(int locationId) {
        for (int i = 0; i < registList.size(); i++) {
            if (registList.get(i).getLocationId() == locationId) {
                return registList.get(i);
            }
        }

        // Exception
        return null;
    }

    public static Regist getMostTotalRentedBikeCount() {
        int idx = -1;
        int max = 0;

        for (int i = 0; i < registList.size(); i++) {
            if (registList.get(i).getTotalRentedBikeCount() > max) {
                idx = i;
                max = registList.get(i).getTotalRentedBikeCount();
            }
        }

        if (idx == -1) return null;
        return registList.get(idx);
    }
}
