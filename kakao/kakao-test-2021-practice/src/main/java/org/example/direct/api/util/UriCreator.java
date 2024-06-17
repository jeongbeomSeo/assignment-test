package org.example.direct.api.util;

import org.springframework.web.util.UriComponentsBuilder;

public class UriCreator {
    private final static String BASE_URL = "https://kox947ka1a.execute-api.ap-northeast-2.amazonaws.com/prod/users";

    public static String startUrl() {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/start")
                .toUriString();
    }

    public static String locationUrl() {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/locations")
                .toUriString();
    }

    public static String truckUri() {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/trucks")
                .toUriString();
    }

    public static String simulateUri() {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/simulate")
                .toUriString();
    }

    public static String scoreUri() {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("/score")
                .toUriString();
    }
}
