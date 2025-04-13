package com.example.SecretSpot.web.dto;

import lombok.Data;

@Data
public class RegionDto {
    private String country;
    private String region;

    public RegionDto(String country, String region) {
        this.country = country;
        this.region = region;
    }
}
