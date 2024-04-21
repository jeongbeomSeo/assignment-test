package practice4.model;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String code;
    private String title;
    private Long credits;
}
