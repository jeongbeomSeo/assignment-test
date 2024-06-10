package org.example.point.register;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class HotPlaceSet {
    private final HotPlace loadedHotPlace;
    private final HotPlace unloadedHotPlace;

    public HotPlaceSet(int loadedHotPlaceId, int unloadedHotPlaceId, int initBikeCount) {
        this.loadedHotPlace = new HotPlace(loadedHotPlaceId, initBikeCount);
        this.unloadedHotPlace = new HotPlace(unloadedHotPlaceId, initBikeCount);
    }
}
