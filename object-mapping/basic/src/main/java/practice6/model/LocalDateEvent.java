package practice6.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalDateEvent {
    private String title;
    private LocalDateTime date;
}