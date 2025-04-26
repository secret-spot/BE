package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegionRecommendDto {
    private String region;
    private String country;
    private List<String> keywords;
}
