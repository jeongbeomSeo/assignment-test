package practice2.model;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private String status;
    private T data;
}
