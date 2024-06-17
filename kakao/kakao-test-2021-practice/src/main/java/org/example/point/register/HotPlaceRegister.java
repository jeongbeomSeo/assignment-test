package org.example.point.register;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
public class HotPlaceRegister {
    @Getter
    private static Set<HotPlaceSet> hotPlaceSets = new HashSet<>();

    public static HotPlaceSet prevHotPlaceSet;

    public static HotPlaceSet getCurrentHotPlaceSet() {
        HotPlaceSet result = null;
        int value = 0;
        for (HotPlaceSet hotPlaceSet : hotPlaceSets) {
            if (value < hotPlaceSet.getLoadedHotPlace().totalLoadedBikeCount + hotPlaceSet.getUnloadedHotPlace().totalUnloaedBikeCount) {
                result = hotPlaceSet;
                value = hotPlaceSet.getLoadedHotPlace().totalLoadedBikeCount + hotPlaceSet.getUnloadedHotPlace().totalUnloaedBikeCount;
            }
        }

        return result;
    }
}
