package com.example.SecretSpot.web.dto.AI;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaceRecommendResponseDto {
    private String region;
    private List<PlaceRecommendItemDto> recommendations;
}