package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NearbyRegionRecommendResponseDto {
    private String region;
    private List<NearbyRegionItemDto> recommendations;
}