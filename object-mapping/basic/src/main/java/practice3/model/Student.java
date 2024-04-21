package practice3.model;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private int age;
}
