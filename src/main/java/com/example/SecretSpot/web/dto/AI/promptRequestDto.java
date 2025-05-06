package com.example.SecretSpot.web.dto.AI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class promptRequestDto {
    private String prompt;
}