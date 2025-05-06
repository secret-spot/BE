package com.example.SecretSpot.web.dto.AI;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceRecommendItemDto {
    private String name;
    private String address;
    private Integer rating;
    private Integer reviews;
}