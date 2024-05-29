package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.ApiClient;
import org.example.api.dto.response.LocationResponse;
import org.example.api.dto.response.StartResponse;
import org.example.api.dto.response.TruckResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class Simulation_1 {

    public static void main(String[] args) {

        final String X_AUTH_TOKEN = "b19ddbc87bd8420e6cc914b09749c35b";
        int time;
        ApiClient apiClient = new ApiClient(new RestTemplate());

        HttpEntity<StartResponse> startResponseHttpEntity = apiClient.startApiCall(X_AUTH_TOKEN, 1);
        StartResponse startResponse = startResponseHttpEntity.getBody();
        log.info(startResponse.toString());

        String authKey = startResponse.getAuthKey();
        time = startResponse.getTime();
        //while (time < 720) {

        HttpEntity<LocationResponse> locationResponseHttpEntity = apiClient.locationApiCall(authKey);
        LocationResponse locationResponse = locationResponseHttpEntity.getBody();
        log.info(locationResponse.toString());

        HttpEntity<TruckResponse> truckResponseHttpEntity = apiClient.truckApiCall(authKey);
        TruckResponse truckResponse = truckResponseHttpEntity.getBody();
        log.info(truckResponse.toString());
        //}
    }
}