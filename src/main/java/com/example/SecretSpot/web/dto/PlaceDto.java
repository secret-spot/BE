package com.example.SecretSpot.web.dto;

import lombok.Data;

@Data
public class PlaceDto {
    private String googlePlaceId;
    private String name;
    private String address;
}
