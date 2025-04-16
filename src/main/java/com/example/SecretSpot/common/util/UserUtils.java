package com.example.SecretSpot.common.util;

import com.example.SecretSpot.domain.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtils {

    //닉네임 존재 시 닉네임, 없을 경우 이름을 반환하는 함수
    public String getNicknameOrName(User user) {
        String nickname = user.getNickname();
        return (nickname != null) ? nickname : user.getName();
    }
}
