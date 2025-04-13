package com.example.SecretSpot.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnalyzeResponseDto {
    private List<String> keywords;
    private List<RegionDto> regions;
}
