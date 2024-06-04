package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Regist {
    private Integer locationId;
    private Integer curBikeCount;
    private Integer totalRentedBikeCount;
}
