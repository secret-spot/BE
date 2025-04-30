package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchResponseDto {
    private String keyword;
    private Boolean isRegion;
    private String etiquette;
    private List<RegionRecommendWithCommentDto> recommendations;
    private List<GuideListItemDto> guides;
}