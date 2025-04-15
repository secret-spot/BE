package com.example.SecretSpot.common.util;

import com.example.SecretSpot.domain.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtils {
    public String getNicknameOrName(User user) {
        String nickname = user.getNickname();
        return (nickname != null) ? nickname : user.getName();
    }
}
