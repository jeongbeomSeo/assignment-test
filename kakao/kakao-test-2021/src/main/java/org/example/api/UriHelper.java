package org.example.api;

import org.springframework.web.util.UriComponentsBuilder;

public class UriHelper {

    private static final String BASE_URL = "https://kox947ka1a.execute-api.ap-northeast-2.amazonaws.com/prod/users";

    public static String getStartUri() {
        return UriComponentsBuilder
                .fromUriString(BASE_URL)
                .path("/start")
                .encode()
                .toUriString();
    }

    public static String getLocationsUri() {
        return UriComponentsBuilder
                .fromUriString(BASE_URL)
                .path("/locations")
                .encode()
                .toUriString();
    }

    public static String getTrucksUri() {
        return UriComponentsBuilder
                .fromUriString(BASE_URL)
                .path("/trucks")
                .encode()
                .toUriString();
    }

    public static String getSimulateUri() {
        return UriComponentsBuilder
                .fromUriString(BASE_URL)
                .path("/simulate")
                .encode()
                .toUriString();
    }

    public static String getScoreUri() {
        return UriComponentsBuilder
                .fromUriString(BASE_URL)
                .path("/score")
                .encode()
                .toUriString();
    }
}
