package practice5.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private String name;
    private Long budget;
    private List<Employee> team;
}
