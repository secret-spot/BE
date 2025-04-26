package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DetailedGuideDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String content;
    private List<String> images;
    private List<PlaceDto> places;
    private List<RegionDto> regions;
    private List<String> keywords;
    private Boolean isMyGuide;
    private Boolean isScraped;
    private String reviewRating;   // 소수점 첫째자리까지만 반환 위해 String
}
