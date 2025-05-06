package com.example.SecretSpot.web.dto.AI;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionRecommendWithCommentDto {
    private String region;
    private String country;
    private String review;
}
