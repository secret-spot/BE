package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GuideListItemDto {
    private Long id;
    private String thumbnailUrl;
    private String title;
    private List<String> regions;
    private Double reviewRating;
    private List<String> keywords;
}
