package com.example.SecretSpot.web.dto.AI;

import com.example.SecretSpot.web.dto.RegionDto;
import lombok.Data;

import java.util.List;

@Data
public class AnalyzeResponseDto {
    private List<String> keywords;
    private List<RegionDto> regions;
}
