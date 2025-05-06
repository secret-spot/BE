package com.example.SecretSpot.web.dto;

import com.example.SecretSpot.web.dto.AI.PlaceRecommendItemDto;
import com.example.SecretSpot.web.dto.AI.RegionRecommendWithCommentDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchResponseDto {
    private String keyword;
    private Boolean isRegion;
    private Boolean isPlace;
    private String etiquette;
    private List<RegionRecommendWithCommentDto> recommendedRegions;
    private List<PlaceRecommendItemDto> recommendedPlaces;
    private List<GuideListItemDto> guides;
}