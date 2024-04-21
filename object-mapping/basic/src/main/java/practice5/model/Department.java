package practice5.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private String name;
    private List<Project> projects;
}
