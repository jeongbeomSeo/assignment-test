package practice3.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School {
    private String name;
    private List<Class> classes;
}
