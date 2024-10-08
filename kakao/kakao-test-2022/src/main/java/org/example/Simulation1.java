package org.example;

import org.example.api.ApiClient;
import org.example.dto.request.ChangeGradeDTO;
import org.example.dto.request.ChangeGradeRequest;
import org.example.dto.request.MatchInfoDTO;
import org.example.dto.response.*;
import org.example.entity.UserInfo;
import org.example.repository.UserInfoRepository;
import org.example.service.MatchingService;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Simulation1 {
    private static final String AUTH_TOKEN = "8e31a4bcec79662aee1af89107916bdb";

    private static UserInfoRepository repository = new UserInfoRepository();
    private static MatchingService service = new MatchingService(repository);
    private static ApiClient apiCelint = new ApiClient(new RestTemplate());

    public static void main(String[] args) {

        System.out.println("Start Sinario 1");

        HttpEntity<StartResponse> startReponse = apiCelint.startApi(1, AUTH_TOKEN);

        StartResponse startReponseBody = startReponse.getBody();

        final String authKey = startReponseBody.getAuthKey();
        final Integer problem = startReponseBody.getProblem();
        Integer time = startReponseBody.getTime();

        while (time <= 595) {
            System.out.println("TIME: " + time);
            // 1. 게임 서버의 사용자 정보 상태 가져오기
            HttpEntity<UserInfoResponse> userInfoResponse = apiCelint.userInfoApi(authKey);

            UserInfoResponse userInfoBody = userInfoResponse.getBody();

            List<UserInfoDTO> userInfoDTOs = userInfoBody.getUserInfo();

            System.out.println("UserInfo: " + userInfoDTOs);

            // 1.1 repository를 이용하여 Grade 모두 업데이트
            repository.updateAll(userInfoDTOs);

            // 2. 게임 끝난 사람 확인
            HttpEntity<GameResultResponse> gameResultResponse = apiCelint.gameResultApi(authKey);

            GameResultResponse gameResultResponseBody = gameResultResponse.getBody();

            List<GameResultDTO> gameReulstList = gameResultResponseBody.getGameReulstList();

            System.out.println("GameResult: " + gameReulstList);

            if (!repository.isInitGrade()) {
                repository.allUpdatedGrade();
                List<ChangeGradeDTO> commands = new ArrayList<>();

                repository.allUpdatedGrade();
                List<UserInfo> userInfos = repository.getAll();
                userInfos.forEach(userInfo -> {
                    commands.add(new ChangeGradeDTO(userInfo.getId(), userInfo.getGrade()));
                    userInfo.changeGradeUpdated();
                });

                apiCelint.changeGradeApi(authKey, new ChangeGradeRequest(commands));
            } else if (!gameReulstList.isEmpty()) {
                // 3. 등급 처리 (필요시 비교 과정 추가)
                gameReulstList.forEach(gameResult -> {
                    repository.updateWinCount(gameResult.getWin());
                    repository.updateLoseCount(gameResult.getLose());
                    // 소모된 시간은 추후에 고려
                });

                // 4. 등급 업데이트 후 API CALL
                HashSet<Integer> updatedIds = new HashSet<>();
                gameReulstList.forEach(gameResult -> {
                    UserInfo winner = repository.getUserInfo(gameResult.getWin());
                    if (winner != null && winner.isUpdated()) {
                        updatedIds.add(winner.getId());
                        winner.changeGradeUpdated();
                    }
                    UserInfo loser = repository.getUserInfo(gameResult.getLose());
                    if (loser != null && loser.isUpdated()) {
                        updatedIds.add(loser.getId());
                        loser.changeGradeUpdated();
                    }
                });

                List<ChangeGradeDTO> commands = updatedIds.stream()
                        .map(updatedId ->
                                new ChangeGradeDTO(updatedId, repository.getUserInfo(updatedId).getGrade())).toList();

                System.out.println("ChangeGrade: " + commands);


                if (!commands.isEmpty()) {
                    ChangeGradeRequest changeGradeRequest = new ChangeGradeRequest(new ArrayList<>(commands));
                    apiCelint.changeGradeApi(authKey, changeGradeRequest);
                }
            }
            // 5. 게임 기다리는 사람 확인
            HttpEntity<WaitingLineResponse> waitingLineResponse = apiCelint.waitingLineApi(startReponseBody.getAuthKey());

            WaitingLineResponse waitingLineResponseBody = waitingLineResponse.getBody();

            List<WaitingUserInfoDTO> waitingUserInfos = waitingLineResponseBody.getWaitingLine();

            System.out.println("waitingUserInfos: " + waitingUserInfos);

            // 6. 매칭 작업 해주기
            MatchInfoDTO result = service.match(waitingUserInfos);

            // 7. API Call
            apiCelint.matchApi(authKey, result);

//            HttpEntity<ScoreResponse> scoreResponse = apiCelint.scoreApi(authKey);
//
//            ScoreResponse scoreResponseBody = scoreResponse.getBody();

//            System.out.println(String.format("status: %s\nefficiency_score:%f\naccuracy_score1:%f\n,accuracy_score2:%f\nscore:%f\n",
//                    scoreResponseBody.getStatus(), scoreResponseBody.getEfficiencyScore(), scoreResponseBody.getAccuracyScore1(),
//                    scoreResponseBody.getAccuracyScore2(), scoreResponseBody.getScore()));
            time++;
        }

        HttpEntity<ScoreResponse> scoreResponse = apiCelint.scoreApi(authKey);

        ScoreResponse scoreResponseBody = scoreResponse.getBody();

        System.out.println("Final Score");
        System.out.println(String.format("status: %s\nefficiency_score:%f\naccuracy_score1:%f\naccuracy_score2:%f\nscore:%f\n",
                scoreResponseBody.getStatus(), scoreResponseBody.getEfficiencyScore(), scoreResponseBody.getAccuracyScore1(),
                scoreResponseBody.getAccuracyScore2(), scoreResponseBody.getScore()));
    }
}
