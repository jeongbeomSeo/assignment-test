package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.api.ApiClient;
import org.example.api.dto.request.SimulateRequestDTO;
import org.example.api.dto.response.*;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class Simulation_1 {

    private static final Integer PROBLEM = 1;
    private static final ApiClient apiClient = new ApiClient(new RestTemplate());
    private static Service service;

    private static final String AUTH_TOKEN = "2328fdb30afbe6f9f62565928ce875ed";

    public static void main(String[] args) {

        log.info("Simulation 1 Start ! ! !");

        service = new ServiceSimulate_1();
//        service = new NothingService();
        Integer time;
        Float failedRequestsCount = 0F;
        Float distance = 0F;

        HttpEntity<StartResponseDTO> startResponseEntity = apiClient.start(AUTH_TOKEN, PROBLEM);

        StartResponseDTO startResponseDTO = startResponseEntity.getBody();

        final String AUTH_KEY = startResponseDTO.getAuthKey();
        time = startResponseDTO.getTime();

        while (time != 720) {

            log.info("Time: {}", time);

            HttpEntity<LocationsResponseDTO> locationsResponseEntity = apiClient.locations(AUTH_KEY);
            LocationsResponseDTO locationsResponseDTO = locationsResponseEntity.getBody();

            log.info(locationsResponseDTO.getLocations().toString());

            HttpEntity<TrucksResponseDTO> trucksResponseEntity = apiClient.trucks(AUTH_KEY);
            TrucksResponseDTO trucksResponseDTO = trucksResponseEntity.getBody();

            log.info(trucksResponseDTO.toString());

            SimulateRequestDTO simulateRequestDTO = service.simulate(PROBLEM, locationsResponseDTO.getLocations(), trucksResponseDTO.getTrucks());

            log.info(simulateRequestDTO.toString());

            HttpEntity<SimulateResponseDTO> simulateResponseEntity = apiClient.simulate(AUTH_KEY, simulateRequestDTO);
            SimulateResponseDTO simulateResponseDTO = simulateResponseEntity.getBody();

            log.info(simulateResponseDTO.toString());
            time = simulateResponseDTO.getTime();
            failedRequestsCount = simulateResponseDTO.getFailedRequestsCount();
            distance = simulateResponseDTO.getDistance();
        }

        if (time == 720) {
            // Log Result

            HttpEntity<ScoreResponseDTO> scoreResponseEntity = apiClient.score(AUTH_KEY);
            Float score = scoreResponseEntity.getBody().getScore();

            log.info("time: {}, failedRequestsCount: {}, distance: {}, score: {}",
                    time, failedRequestsCount, distance, score);
        } else {
            log.warn("Logic Error: 타임이 720초까지 진행하지 못함");
        }
    }
}