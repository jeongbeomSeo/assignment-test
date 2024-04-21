package practice8.model;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArrayElement {
    private int index1;
    private int index2;
    private Category category;
}
