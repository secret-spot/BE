package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewCreateDto {
    private Byte rating;
    private String content;
}