package practice5.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role{
    Lead("LEAD"),
    Dev("DEV");

    private final String role;

    @JsonCreator // Json으로 들어오는 문자열이 name(Lead, Dev)과 매칭되어야 한다.
    public static Role forValue(String value) {
        return Role.valueOf(value);
    }
}
