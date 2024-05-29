package org.example.api.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.response.LocationResponse;
import org.example.api.dto.response.StartResponse;
import org.example.api.dto.response.TruckResponse;
import org.example.api.util.UriCreator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ApiClient {
    private final RestTemplate restTemplate;

    public HttpEntity<StartResponse> startApiCall(String authTokenKey, Integer problem) {
        String uri = UriCreator.startUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", authTokenKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Integer> body = new HashMap<>();
        body.put("problem", problem);
        HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

        log.info("Api call, Api = start, Problem = {}", problem);
        return restTemplate.exchange(uri, HttpMethod.POST, entity, StartResponse.class);
    }

    public HttpEntity<LocationResponse> locationApiCall(String authKey) {
        String uri = UriCreator.locationUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        log.info("Api call, Api = location");
        return restTemplate.exchange(uri, HttpMethod.GET, entity, LocationResponse.class);
    }

    public HttpEntity<TruckResponse> truckApiCall(String authKey) {
        String uri = UriCreator.truckUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        log.info("Api call, Api = truck");
        return restTemplate.exchange(uri, HttpMethod.GET, entity, TruckResponse.class);
    }

}
