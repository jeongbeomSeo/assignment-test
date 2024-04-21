package practice5.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private String name;
    private List<Department> departments;
}
