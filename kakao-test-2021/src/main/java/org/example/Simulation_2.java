package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.api.dto.ApiClient;
import org.example.api.dto.request.CommandsRequest;
import org.example.api.dto.response.*;
import org.example.model.HotPlaceInfo;
import org.example.model.Regist;
import org.example.service1.MostToLeastServiceImpl;
import org.example.service1.NearBySomeServiceImpl;
import org.example.service2.Service;
import org.example.service2.ServiceImpl;
import org.example.util.UtilHotPlace;
import org.example.util.UtilRegist;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
public class Simulation_2 {
    private static final Integer problem = 2;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ApiClient apiClient = new ApiClient(new RestTemplate());


    public static void main(String[] args) throws IOException {
        final String X_AUTH_TOKEN = "b19ddbc87bd8420e6cc914b09749c35b";
        Float totalFailCount = 0F;
        Float totalDistance = 0F;
//        Service service = new NothingServiceImpl();
//        Service service = new MostToLeastServiceImpl();
//        Service service = new NearBySomeServiceImpl();
        Service service = new ServiceImpl();

        // Json Parsing
        for (int i = 1; i <= 3; i++) {
            String jsonName = String.format("problem2_day-%d.json", i);
            String path = "json/" + jsonName;
            log.info("Path = {}", path);

            File file = new File(path);
            Map<Integer, List<List<Integer>>> history = objectMapper.readValue(file, new TypeReference<>() {});

            log.info("Parsing 결과 = {}, history 크기 = {}", !history.isEmpty(), history.size());
            for (int pat = 0; pat < 3; pat++) {
                Map<Integer, Integer> loadedCountMap = new HashMap<>();
                Map<Integer, Integer> unloadedCountMap = new HashMap<>();
                for (int time = (60 * 4) * pat; time < (60 * 4) * (pat + 1); time++) {
                    List<List<Integer>> values = history.get(time);
                    for (List<Integer> value : values) {
                        loadedCountMap.put(value.get(0),
                                loadedCountMap.getOrDefault(value.get(0), 0) + 1);
                        unloadedCountMap.put(value.get(1),
                                unloadedCountMap.getOrDefault(value.get(1), 0) + 1);
                    }
                }
                int rentalId = getMostCountId(loadedCountMap);
                int returnId = getMostCountId(unloadedCountMap);
                UtilHotPlace.addHotPlaceInfo(new HotPlaceInfo(rentalId, returnId));
            }
        }

        log.info("Hop Place Info 갯수 = {}", UtilHotPlace.getHotPlaceInfo().size());
        for (HotPlaceInfo hotPlaceInfo : UtilHotPlace.getHotPlaceInfo()) {
            log.info("Hot Place = {}", hotPlaceInfo.toString());
            UtilRegist.addNewRegist(hotPlaceInfo.getRentalLocationId(), 1);
        }

        HttpEntity<StartResponse> startHttpEntity = apiClient.startApiCall(X_AUTH_TOKEN, problem);
        StartResponse startResponse = startHttpEntity.getBody();
        log.info(startResponse.toString());

        String authKey = startResponse.getAuthKey();
        int time = startResponse.getTime();

        while (time < 720) {
            HttpEntity<LocationResponse> locationHttpEntity = apiClient.locationApiCall(authKey);
            LocationResponse locationResponse = locationHttpEntity.getBody();
            // log.info(locationResponse.toString());

            HttpEntity<TruckResponse> truckHttpEntity = apiClient.truckApiCall(authKey);
            TruckResponse truckResponse = truckHttpEntity.getBody();
            log.info(truckResponse.toString());

            // 매 분마다 업데이트 및 240분마다 TotalRentalBikeCount 초기화
            UtilRegist.updateRegist(locationResponse.getLocations());
            if (time % 240 == 0) {
                UtilRegist.resetTotalRentedBikeCount();
            }

            Regist hotPlaceRegist = UtilRegist.getMostTotalRentedBikeCount();
            int[] times = new int[3600];
            Arrays.fill(times, 60);

            CommandsRequest commandsRequest = null;

            if (hotPlaceRegist == null) {
                // HotPlace List를 건내주고, 갯수를 1개 이상으로 맞춰주기
                commandsRequest = service.simuateHotPlace(
                        truckResponse.getTrucks(), locationResponse.getLocations(), UtilHotPlace.getHotPlaceInfo(), times);
                commandsRequest = service.simulate(truckResponse.getTrucks(), locationResponse.getLocations(), null, times, commandsRequest.getCommands());
            }
            else {
                // 기본 로직
                HotPlaceInfo hotPlaceInfo = UtilHotPlace.getHotPlace(UtilRegist.getMostTotalRentedBikeCount().getLocationId());
                log.info("현재 타임 = {}, Hot Place = {}", time, hotPlaceInfo.toString());
                commandsRequest = service.simulate(truckResponse.getTrucks(), locationResponse.getLocations(), UtilHotPlace.getHotPlace(UtilRegist.getMostTotalRentedBikeCount().getLocationId()), times, null);
            }

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
결과 기록
1. NothingServiceImpl
Service = class org.example.service.NothingServiceImpl
Total Fail Count = 1687.0
Total Distance = 0.0
Score = 596.4027

2. MostToLeastServiceImpl (Simulation 1의 로직 그대로)
Service = class org.example.service.MostToLeastServiceImpl
Total Fail Count = 1693.0
Total Distance = 10.4
Score = 595.9837
=> Simulation 1의 로직은 5 X 5의 크기와 전체 맵 크기 대비 자전거 수가 많고 요청 수가 적은 것을 이용하여 로직을 구성하였기 때문에
해당 코드를 사용하기 힘들다.
실제로 UtilTruck의 findNearestTruck의 코드를 보면 이동 가능한 트럭을 선정해서 이동시키는 로직으로 구성되어 있는데, 해당 로직은 시물레이션 2에서 적용될 경우 트럭 선정이 거의 불가능하다 싶히 적용된다.



3

 */