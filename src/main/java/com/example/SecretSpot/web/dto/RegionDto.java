package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionDto {
    private String country;
    private String region;

    public RegionDto(String country, String region) {
        this.country = country;
        this.region = region;
    }
}
