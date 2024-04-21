package practice4.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private String name;
    private List<Course> courses;
    private Head head;
}
