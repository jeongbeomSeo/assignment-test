package org.example.api;

import lombok.RequiredArgsConstructor;
import org.example.UriEnum;
import org.example.dto.request.ChangeGradeRequest;
import org.example.dto.request.MatchInfoDTO;
import org.example.dto.response.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ApiClient {

    private final RestTemplate restTemplate;

    public HttpEntity<StartResponse> startApi(Integer problem, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", authToken);
        headers.setContentType(UriEnum.START.getContentType());
        Map<String, Integer> body = new HashMap<>();
        body.put("problem", problem);

        HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(UriEnum.START.getUri(), UriEnum.START.getMETHOD(), entity, StartResponse.class);
    }

    public HttpEntity<WaitingLineResponse> waitingLineApi(String authKey) {
        UriEnum uriEnum = UriEnum.WAITING_LINE;
        HttpHeaders headers = getHeaders(uriEnum, authKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uriEnum.getUri(), uriEnum.getMETHOD(), entity, WaitingLineResponse.class);
    }

    public HttpEntity<GameResultResponse> gameResultApi(String authKey) {
        UriEnum uriEnum = UriEnum.GAME_RESULT;
        HttpHeaders headers = getHeaders(uriEnum, authKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uriEnum.getUri(), uriEnum.getMETHOD(), entity, GameResultResponse.class);
    }

    public HttpEntity<UserInfoResponse> userInfoApi(String authKey) {
        UriEnum uriEnum = UriEnum.USER_INFO;
        HttpHeaders headers = getHeaders(uriEnum, authKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uriEnum.getUri(), uriEnum.getMETHOD(), entity, UserInfoResponse.class);
    }

    public HttpEntity<MatchResponse> matchApi(String authKey, MatchInfoDTO body) {
        UriEnum uriEnum = UriEnum.MATCH;
        HttpHeaders headers = getHeaders(uriEnum, authKey);
        HttpEntity<MatchInfoDTO> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(uriEnum.getUri(), uriEnum.getMETHOD(), entity, MatchResponse.class);
    }

    public HttpEntity<ChangeGradeResponse> changeGradeApi(String authKey, ChangeGradeRequest body) {
        UriEnum uriEnum = UriEnum.CHANGE_GRADE;
        HttpHeaders headers = getHeaders(uriEnum, authKey);
        HttpEntity<ChangeGradeRequest> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(uriEnum.getUri(), uriEnum.getMETHOD(), entity, ChangeGradeResponse.class);
    }

    public HttpEntity<ScoreResponse> scoreApi(String authKey) {
        UriEnum uriEnum = UriEnum.SCORE;
        HttpHeaders headers = getHeaders(uriEnum, authKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uriEnum.getUri(), uriEnum.getMETHOD(), entity, ScoreResponse.class);
    }

    private HttpHeaders getHeaders(UriEnum uriEnum, String authKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authKey);
        headers.setContentType(uriEnum.getContentType());

        return headers;
    }

}
