package com.example.SecretSpot.web.dto.AI;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionRecommendItemDto {
    private String smallCity;
    private String shortReview;
}