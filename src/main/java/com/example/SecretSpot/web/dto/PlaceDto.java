package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceDto {
    private String googlePlaceId;
    private String name;
    private String address;
}
