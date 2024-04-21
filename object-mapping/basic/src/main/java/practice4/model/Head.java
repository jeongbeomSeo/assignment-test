package practice4.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Head extends Professor{
    private List<String> publications;
}
