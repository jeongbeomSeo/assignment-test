package practice4.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class University {
    private String name;
    private List<Department> departments;
}
