package org.example.point;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.point.api.ApiClient;
import org.example.point.api.dto.*;
import org.example.point.model.Location;
import org.example.point.model.Truck;
import org.example.point.register.HotPlace;
import org.example.point.register.HotPlaceRegister;
import org.example.point.register.HotPlaceSet;
import org.example.point.register.TruckInfoRegister;
import org.example.point.service.PointSimulateServiceImpl;
import org.example.point.service.Service;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
public class Simulation {
    private static final String X_AUTH_TOKEN = "b19ddbc87bd8420e6cc914b09749c35b";
    private static final Integer PROBLEM_NUMBER = 2;
    private static ApiClient apiClient;
    private static Service service;
    private static TruckInfoRegister truckInfoRegister;
    private static ObjectMapper objectMapper;

    public static void main(String[] args) throws IOException {

        // 초기화
        apiClient = new ApiClient(new RestTemplate());
        objectMapper = new ObjectMapper();
//        service = new Service(new NothingSimuateServiceImpl());
        service = new Service(new PointSimulateServiceImpl());
        truckInfoRegister = new TruckInfoRegister();
        truckInfoRegister.initTruckInfos(PROBLEM_NUMBER);

        Float totalFailCount = 0F;
        Float totalDistance = 0F;

        // Start
        HttpEntity<StartResponse> startResponseHttpEntity = apiClient.start(PROBLEM_NUMBER, X_AUTH_TOKEN);
        StartResponse startResponse = startResponseHttpEntity.getBody();
        log.info(startResponse.toString());

        final String AUTH_KEY = startResponse.getAuthKey();
        Integer time = startResponse.getTime();

        if (PROBLEM_NUMBER == 2) {
            // Hot Place
            for (int i = 1; i <= 3; i++) {
                String jsonName = String.format("problem2_day-%d.json", i);
                String path = "json/" + jsonName;

                File file = new File(path);
                Map<Integer, List<List<Integer>>> history = objectMapper.readValue(file, new TypeReference<Map<Integer, List<List<Integer>>>>() {});

                for (int pat = 0; pat < 3; pat++) {
                    Map<Integer, Integer> loadedCountMap = new HashMap<>();
                    Map<Integer, Integer> unloadedCountMap = new HashMap<>();

                    for (int t = (60 * 4) * pat; t < (60 * 4) * (pat + 1); t++) {
                        List<List<Integer>> values = history.get(t);
                        for (List<Integer> value : values) {
                            loadedCountMap.put(value.get(0),
                                    loadedCountMap.getOrDefault(value.get(0), 0) + 1);
                            unloadedCountMap.put(value.get(1),
                                    unloadedCountMap.getOrDefault(value.get(1), 0) + 1);
                        }
                    }
                    int rentalId = getMostCountId(loadedCountMap);
                    int returnId = getMostCountId(unloadedCountMap);

                    HotPlaceRegister.getHotPlaceSets().add(new HotPlaceSet(rentalId, returnId, PROBLEM_NUMBER == 1 ? 4 : 3));
                }
            }
            log.info(HotPlaceRegister.getHotPlaceSets().toString());
        }

        while (time < 720) {
            // locations
            HttpEntity<LocationsResponse> locationsResponseHttpEntity = apiClient.locations(AUTH_KEY);
            List<Location> locations = locationsResponseHttpEntity.getBody().getLocations();
            // log.info(locations.toString());

            if (PROBLEM_NUMBER == 2) {
                // HotPlace Update
                Set<HotPlaceSet> hotPlaceSets = HotPlaceRegister.getHotPlaceSets();
                if (time % 240 == 0) {
                    // 모든 Hot Place Set 초기화
                    for (HotPlaceSet hotPlaceSet : hotPlaceSets) {
                        hotPlaceSet.getLoadedHotPlace().initInfo();
                        hotPlaceSet.getUnloadedHotPlace().initInfo();
                    }
                }

                for (HotPlaceSet hotPlaceSet : hotPlaceSets) {
                    HotPlace loadedHotPlace = hotPlaceSet.getLoadedHotPlace();
                    Location loadedLocation = locations.get(loadedHotPlace.locationId);
                    updateTotalHotPlace(loadedHotPlace, loadedLocation);

                    HotPlace unloadedHotPlace = hotPlaceSet.getUnloadedHotPlace();
                    Location unloadedLocation = locations.get(unloadedHotPlace.locationId);
                    updateTotalHotPlace(unloadedHotPlace, unloadedLocation);
                }
            }

            // Trucks
            HttpEntity<TrucksResponse> trucksResponseHttpEntity = apiClient.trucks(AUTH_KEY);
            List<Truck> trucks = trucksResponseHttpEntity.getBody().getTrucks();
            // log.info(trucks.toString());

            // Simulate
            SimulateRequest simulateRequest = service.createSimulateRequest(PROBLEM_NUMBER, time, locations, trucks);
            log.info("Truck Command");
            log.info(simulateRequest.getCommands().toString());
            HttpEntity<SimulateResponse> simulateResponseHttpEntity = apiClient.simulate(AUTH_KEY, simulateRequest);
            SimulateResponse simulateResponse = simulateResponseHttpEntity.getBody();
            log.info(simulateResponse.toString());

            // Hot Place prev Time 업데이트
            if (PROBLEM_NUMBER == 2) {
                // HotPlace Update
                Set<HotPlaceSet> hotPlaceSets = HotPlaceRegister.getHotPlaceSets();
                for (HotPlaceSet hotPlaceSet : hotPlaceSets) {
                    HotPlace loadedHotPlace = hotPlaceSet.getLoadedHotPlace();
                    Location loadedLocation = locations.get(loadedHotPlace.locationId);
                    loadedHotPlace.prevTimeBikeCount = loadedLocation.getLocatedBikesCount();

                    HotPlace unloadedHotPlace = hotPlaceSet.getUnloadedHotPlace();
                    Location unloadedLocation = locations.get(unloadedHotPlace.locationId);
                    unloadedHotPlace.prevTimeBikeCount = unloadedLocation.getLocatedBikesCount();
                }
            }

            time = simulateResponse.getTime();
            totalFailCount = simulateResponse.getFailedRequestsCount();
            totalDistance = simulateResponse.getDistance();
        }

        if (time == 720) {
            HttpEntity<ScoreResponse> scoreHttpEnity = apiClient.score(AUTH_KEY);
            log.info("Service = {}, Total Fail Count = {}, Total Distance = {}, Score = {}", service.getClass(), totalFailCount, totalDistance, scoreHttpEnity.getBody().getScore());
        }
    }
    private static void updateTotalHotPlace(HotPlace hotPlace, Location location) {

        if (hotPlace.prevTimeBikeCount > location.getLocatedBikesCount()) {
            hotPlace.totalLoadedBikeCount += hotPlace.prevTimeBikeCount - location.getLocatedBikesCount();
        } else {
            hotPlace.totalUnloaedBikeCount += location.getLocatedBikesCount() - hotPlace.prevTimeBikeCount;
        }
        hotPlace.prevTimeBikeCount = location.getLocatedBikesCount();
    }
    private static int getMostCountId(Map<Integer, Integer> countMap) {

        List<Integer> keyList = new ArrayList<>(countMap.keySet());
        int mostId = -1;
        int mostCount = -1;
        for (Integer key : keyList) {
            if (mostCount < countMap.get(key)) {
                mostId = key;
                mostCount = countMap.get(key);
            }
        }

        return mostId;
    }
}
/*
1. PointSimulateServiceImpl
Problem = 1
Service = class org.example.point.service.Service,
Total Fail Count = 105.0
Total Distance = 2757.5
Score = 267.55453

2. PointSimulateServiceImpl
Problem = 2
Service = class org.example.point.service.Service
Total Fail Count = 1481.0
Total Distance = 2129.0
Score = 598.7037

3. PointSimulateServiceImpl
풀이: 각 location의 bike 수의 Lower boundary 값을 좀 더 높게 설정한 풀이
Problem = 2
Service = class org.example.point.service.Service,
Total Fail Count = 1127.0
Total Distance = 5835.8
Score = 602.4234
 */