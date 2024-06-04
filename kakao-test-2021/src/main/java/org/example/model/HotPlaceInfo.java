package org.example.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class HotPlaceInfo {
    private final int rentalLocationId;
    private final int returnLocationId;
}
