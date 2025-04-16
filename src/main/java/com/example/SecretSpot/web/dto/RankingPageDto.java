package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RankingPageDto {
    private int rank;
    private String nickname;
    private String profileImageUrl;
    private List<String> keywords;
}