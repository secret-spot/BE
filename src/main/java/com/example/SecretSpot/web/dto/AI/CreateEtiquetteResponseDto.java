package com.example.SecretSpot.web.dto.AI;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateEtiquetteResponseDto {
    private String region;
    private String content;
}