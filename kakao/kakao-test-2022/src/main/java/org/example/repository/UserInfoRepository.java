package org.example.repository;

import org.example.dto.response.UserInfoDTO;
import org.example.entity.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoRepository {
    private boolean isInit = false;
    private boolean isGradeInit = false;

    private final Map<Integer, UserInfo> register = new HashMap<>();

    public void updateWinCount(Integer id) {
        if (!existsById(id)) return;

        UserInfo userInfo = register.get(id);
        userInfo.updateWinCount();
    }

    public void updateLoseCount(Integer id) {
        if (!existsById(id)) return;

        UserInfo userInfo = register.get(id);
        userInfo.updateLoseCount();
    }

    public boolean existsById(Integer id) {
        return register.containsKey(id);
    }

    public UserInfo add(Integer id) {
        if (existsById(id)) return register.get(id);

        UserInfo userInfo = UserInfo.builder()
                .id(id)
                .winCount(0)
                .loseCount(0)
                .grade(0)
                .build();

        register.put(id, userInfo);

        return userInfo;
    }
    public UserInfo getUserInfo(Integer id) {
        return register.getOrDefault(id, null);
    }

    public UserInfo update(UserInfo userInfo) {
        register.put(userInfo.getId(), userInfo);

        return userInfo;
    }

    public boolean isInitGrade() {
        return this.isGradeInit;
    }

    public void allUpdatedGrade() {
        if (isGradeInit) return;

        register.values().forEach(UserInfo::initGrade);

        isGradeInit = true;
    }

    public List<UserInfo> getAll() {
        return register.values().stream().toList();
    }

    public void updateAll(List<UserInfoDTO> userInfoDTOs) {
        if (setInit(userInfoDTOs)) return;

        userInfoDTOs.forEach(userInfoDTO -> {
            UserInfo userInfo = register.get(userInfoDTO.getId());
            userInfo.updateGrade(userInfo.getGrade());
        });
    }

    public boolean setInit(List<UserInfoDTO> userInfoDTOs) {
        if (isInit) return false;

        userInfoDTOs.forEach(userInfoDTO -> {
            register.put(userInfoDTO.getId(), UserInfo.builder()
                    .id(userInfoDTO.getId())
                    .winCount(0)
                    .loseCount(0)
                    .grade(userInfoDTO.getGrade())
                    .build());
        });

        isInit = true;

        return true;
    }
}
