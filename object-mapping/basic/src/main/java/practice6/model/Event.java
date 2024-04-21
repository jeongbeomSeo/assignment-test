package practice6.model;

import lombok.*;

import java.util.Date;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String title;
    private Date date;
}
