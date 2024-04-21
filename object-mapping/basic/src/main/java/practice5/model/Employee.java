package practice5.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String name;
    private Role role;
    private List<String> skills;
}
