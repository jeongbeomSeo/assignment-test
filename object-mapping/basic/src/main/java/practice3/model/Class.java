package practice3.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    private int grade;
    private List<Student> students;
}
