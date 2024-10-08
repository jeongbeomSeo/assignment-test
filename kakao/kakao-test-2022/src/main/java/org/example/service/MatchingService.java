package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.MatchingUtils;
import org.example.dto.MatchingDTO;
import org.example.dto.request.MatchInfoDTO;
import org.example.dto.response.WaitingUserInfoDTO;
import org.example.entity.UserInfo;
import org.example.repository.UserInfoRepository;

import java.util.List;
import java.util.PriorityQueue;

@RequiredArgsConstructor
public class MatchingService {

    private final UserInfoRepository repository;

    public MatchInfoDTO match(List<WaitingUserInfoDTO> waitingUserInfos) {

        MatchInfoDTO result = new MatchInfoDTO();

        if (waitingUserInfos.isEmpty()) return result;

        PriorityQueue<MatchingDTO> pq = new PriorityQueue<>(new MatchingUtils.BasicMatchingCompare());

        waitingUserInfos.forEach(waitingUserInfoDTO -> {
            UserInfo userInfo = repository.getUserInfo(waitingUserInfoDTO.getId());
            MatchingDTO matchingDTO = new MatchingDTO(userInfo.getId(), userInfo.getGrade(), waitingUserInfoDTO.getFrom());
            pq.add(matchingDTO);
        });

        while (pq.size() >= 2) {
            MatchingDTO matchingDTO1 = pq.poll();
            MatchingDTO matchingDTO2 = pq.poll();

            result.addPair(matchingDTO1.getId(), matchingDTO2.getId());
        }

        return result;
    }
}
