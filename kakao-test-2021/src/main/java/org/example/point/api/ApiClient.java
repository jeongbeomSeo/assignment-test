package org.example.point.api;

import lombok.AllArgsConstructor;
import org.example.direct.api.dto.response.LocationResponse;
import org.example.point.api.dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ApiClient {
    private static final String BASE_URL = "https://kox947ka1a.execute-api.ap-northeast-2.amazonaws.com/prod/users";

    private final RestTemplate restTemplate;

    public HttpEntity<StartResponse> start(Integer problem, String authToken) {
        String uri = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .path("/start")
                .encode()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Integer> body = new HashMap<>();
        body.put("problem", problem);
        HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(uri, HttpMethod.POST, entity, StartResponse.class);
    }

    public HttpEntity<LocationsResponse> locations(String authKey) {
        String uri = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .path("/locations")
                .encode()
                .toUriString();

        HttpHeaders headers = getAuthHeaders(authKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, entity, LocationsResponse.class);
    }

    public HttpEntity<TrucksResponse> trucks(String authKey) {
        String uri = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .path("/trucks")
                .encode()
                .toUriString();

        HttpHeaders headers = getAuthHeaders(authKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, entity, TrucksResponse.class);
    }

    public HttpEntity<SimulateResponse> simulate(String authKey, SimulateRequest simulateRequest) {
        String uri = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .path("/simulate")
                .encode()
                .toUriString();

        HttpHeaders headers = getAuthHeaders(authKey);
        HttpEntity<SimulateRequest> entity = new HttpEntity<>(simulateRequest, headers);

        return restTemplate.exchange(uri, HttpMethod.PUT, entity, SimulateResponse.class);
    }

    public HttpEntity<ScoreResponse> score(String authKey) {
        String uri = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .path("/score")
                .encode()
                .toUriString();

        HttpHeaders headers = getAuthHeaders(authKey);
        HttpEntity<SimulateRequest> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, entity, ScoreResponse.class);
    }

    private HttpHeaders getAuthHeaders(String authKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
