package com.example.SecretSpot.web.dto.AI;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckIsRegionResponseDto {
    private String prompt;
    private CheckIsRegionItemDto result;
}