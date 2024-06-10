package org.example.point.register;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TruckInfoRegister {
    @Getter
    private static final List<TruckInfo> truckInfos = new ArrayList<>();

    public void initTruckInfos(Integer problem) {
        if (problem == 1) {
           int[] boundary = new int[] {0, 2, 4};
           for (int i = 0; i < 5; i++) {
               if (i == 0) {
                   truckInfos.add(i, new TruckInfo(i, 4, 0, 0, 4, problem));
               } else {
                   int ri = (i - 1) % 2;
                   int ci = (i - 1) / 2;
                   truckInfos.add(i, new TruckInfo(i, boundary[ri + 1], boundary[ri], boundary[ci], boundary[ci + 1], problem));
               }
           }
        } else {
           int[] bounday = new int[] {0, 20, 40, 59};
           for (int i = 0; i < 10; i++) {
               if (i == 0 || i == 1) {
                   truckInfos.add(i, new TruckInfo(i, 59, 0, 0, 59, problem));
                   if (i == 0) truckInfos.get(i).setLoading(true);
               } else {
                   int ri = (i - 2) % 3;
                   int ci = (i - 2) % 3;
                   truckInfos.add(i, new TruckInfo(i, bounday[ri + 1], bounday[ri], bounday[ci], bounday[ci + 1], problem));
               }
           }
        }
    }

}
