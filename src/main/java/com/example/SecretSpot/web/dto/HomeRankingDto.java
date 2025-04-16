package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeRankingDto {
    private final int rank;
    private final String nickname;
    private final String profileImageUrl;
}