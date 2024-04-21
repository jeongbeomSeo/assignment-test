package practice8.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// @JsonIgnoreProperties(value = {"value"})
public class Configuration {
    private String name;
    private List<ArrayElement> array;
}
