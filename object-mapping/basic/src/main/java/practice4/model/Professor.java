package practice4.model;


import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Professor {
    private String name;
    private int age;
}
