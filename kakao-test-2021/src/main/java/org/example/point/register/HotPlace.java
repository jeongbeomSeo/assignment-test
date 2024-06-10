package org.example.point.register;

import lombok.ToString;

@ToString
public class HotPlace {
    public final Integer locationId;
    public Integer totalLoadedBikeCount;
    public Integer totalUnloaedBikeCount;
    public Integer prevTimeBikeCount;

    public HotPlace(Integer locationId, Integer prevTimeBikeCount) {
        this.locationId = locationId;
        this.prevTimeBikeCount = prevTimeBikeCount;
        this.totalLoadedBikeCount = 0;
        this.totalUnloaedBikeCount = 0;
    }

    public void initInfo() {
        this.totalLoadedBikeCount = 0;
        this.totalUnloaedBikeCount = 0;
    }
}
