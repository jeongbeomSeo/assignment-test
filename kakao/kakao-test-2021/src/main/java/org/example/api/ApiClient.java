package org.example.api;

import org.example.api.dto.request.SimulateRequestDTO;
import org.example.api.dto.response.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.*;

public class ApiClient {

    private final RestTemplate restTemplate;

    public ApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HttpEntity<StartResponseDTO> start(String authToken, Integer problem) {

        String uri = UriHelper.getStartUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Auth-Token", authToken);
        Map<String, Integer> body = new HashMap<>();
        body.put("problem", problem);
        HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(uri, POST, entity, StartResponseDTO.class);
    }

    public HttpEntity<LocationsResponseDTO> locations(String authKey) {

        String uri = UriHelper.getLocationsUri();
        HttpHeaders headers = getAuthorizationHeaders(authKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, GET, entity, LocationsResponseDTO.class);
    }

    public HttpEntity<TrucksResponseDTO> trucks(String authKey) {

        String uri = UriHelper.getTrucksUri();
        HttpHeaders headers = getAuthorizationHeaders(authKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, GET, entity, TrucksResponseDTO.class);
    }

    public HttpEntity<SimulateResponseDTO> simulate(String authKey, SimulateRequestDTO simulateRequestDTO) {

        String uri = UriHelper.getSimulateUri();
        HttpHeaders headers = getAuthorizationHeaders(authKey);

        HttpEntity<SimulateRequestDTO> entity = new HttpEntity<>(simulateRequestDTO, headers);

        return restTemplate.exchange(uri, PUT, entity, SimulateResponseDTO.class);
    }

    public HttpEntity<ScoreResponseDTO> score(String authKey) {

        String uri = UriHelper.getScoreUri();
        HttpHeaders headers = getAuthorizationHeaders(authKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, GET, entity, ScoreResponseDTO.class);
    }

    private HttpHeaders getAuthorizationHeaders(String authKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", authKey);

        return headers;
    }
}
