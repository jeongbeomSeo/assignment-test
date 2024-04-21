package practice1.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Manager.class, name = "Manager"),
        @JsonSubTypes.Type(value = Clerk.class,  name = "Clerk")
})
public class Employee {
    private String type;
    private String name;
    private int age;
    private String department;
}