package practice7.model;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponseDTO<T> {
    private String message;
    private T content;
}
