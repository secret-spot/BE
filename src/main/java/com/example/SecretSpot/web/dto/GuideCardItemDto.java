package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GuideCardItemDto {
    private Long id;
    private Boolean isScraped;
    private String thumbnailUrl;
    private String title;
    private List<String> regions;
    private List<String> keywords;
}
