package com.example.SecretSpot.web.dto;

import lombok.Getter;

@Getter
public class TokenDto {
    String accessToken;
    String refreshToken;

    public TokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
