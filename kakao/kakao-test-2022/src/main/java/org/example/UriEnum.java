package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@Getter
@AllArgsConstructor
public enum UriEnum {

    START(HttpMethod.POST, "/start"),
    WAITING_LINE(HttpMethod.GET, "/waiting_line"),
    GAME_RESULT(HttpMethod.GET, "/game_result"),
    USER_INFO(HttpMethod.GET, "/user_info"),
    MATCH(HttpMethod.PUT, "/match"),
    CHANGE_GRADE(HttpMethod.PUT, "/change_grade"),
    SCORE(HttpMethod.GET, "/score");

    private final String BASE_URL = "https://huqeyhi95c.execute-api.ap-northeast-2.amazonaws.com/prod";
    private final MediaType contentType = MediaType.APPLICATION_JSON;
    private final HttpMethod METHOD;
    private final String uri;

    public String getUri() {
        return BASE_URL + uri;
    }

}


