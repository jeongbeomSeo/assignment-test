package org.example.dto.response;

import lombok.Data;

@Data
public class UserInfoDTO {
    private Integer id;     // 유저의 아이디
    private Integer grade;  // 현재 유저의 등급
}
