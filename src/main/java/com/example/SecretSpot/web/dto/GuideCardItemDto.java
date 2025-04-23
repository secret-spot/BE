package com.example.SecretSpot.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GuideCardItemDto {
    private Long id;
    private String thumbnailUrl;
    private String title;
    private List<String> regions;
    private List<String> keywords;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isScraped;
}
