package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.ApiClient;
import org.example.api.dto.request.CommandsRequest;
import org.example.api.dto.response.*;
import org.example.service.MostToLeastServiceImpl;
import org.example.service.NothingServiceImpl;
import org.example.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class Simulation_1 {

    public static void main(String[] args) {

        final String X_AUTH_TOKEN = "b19ddbc87bd8420e6cc914b09749c35b";
        int time;
        Float totalFailCount = 0F;
        Float totalDistance = 0F;
        ApiClient apiClient = new ApiClient(new RestTemplate());
//        Service service = new NothingServiceImpl();
        Service service = new MostToLeastServiceImpl();

        HttpEntity<StartResponse> startHttpEntity = apiClient.startApiCall(X_AUTH_TOKEN, 1);
        StartResponse startResponse = startHttpEntity.getBody();
        log.info(startResponse.toString());

        String authKey = startResponse.getAuthKey();
        time = startResponse.getTime();
        while (time < 720) {
            HttpEntity<LocationResponse> locationHttpEntity = apiClient.locationApiCall(authKey);
            LocationResponse locationResponse = locationHttpEntity.getBody();
            log.info(locationResponse.toString());

            HttpEntity<TruckResponse> truckHttpEntity = apiClient.truckApiCall(authKey);
            TruckResponse truckResponse = truckHttpEntity.getBody();
            log.info(truckResponse.toString());

            CommandsRequest commandsRequest = service.simulate(truckResponse.getTrucks(), locationResponse.getLocations());

            HttpEntity<SimulateResponse> simulateHttpEntity = apiClient.simulateApiCall(authKey, commandsRequest);
            SimulateResponse simulateResponse = simulateHttpEntity.getBody();
            log.info(simulateResponse.toString());

            time = simulateResponse.getTime();
            totalFailCount = simulateResponse.getFailedRequestsCount();
            totalDistance = simulateResponse.getDistance();
         }

        if (time == 720) {
            HttpEntity<ScoreResponse> scoreHttpEnity = apiClient.scoreApiCall(authKey);
            log.info("Service = {}, Total Fail Count = {}, Total Distance = {}, Score = {}", service.getClass(), totalFailCount, totalDistance, scoreHttpEnity.getBody().getScore());
        }
    }
}
/*
결과 기록
1. NothingServiceImpl
Service = class org.example.service.NothingServiceImpl
Total Fail Count = 351.0
Total Distance = 0.0
Score = 229.94748

2. MostToLeastServiceImpl
Service = class org.example.service.MostToLeastServiceImpl
Total Fail Count = 85.0
Total Distance = 1153.0
Score = 278.23154
 */