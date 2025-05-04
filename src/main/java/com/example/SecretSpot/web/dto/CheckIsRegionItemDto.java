package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckIsRegionItemDto {
    private Boolean isRegion;
    private Boolean isPlace;
}