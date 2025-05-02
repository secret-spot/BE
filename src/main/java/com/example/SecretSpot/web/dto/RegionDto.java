package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionDto {
    private String country;
    private String region;

    public RegionDto(String key, String value) {
        this.country = key;
        this.region = value;
    }
}
