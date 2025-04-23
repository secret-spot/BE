package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewCreateResponseDto {
    private Long id;
    private Byte rating;
    private String content;
    private LocalDateTime createdAt;
    private Double averageRating;
    private int totalReviewCount;
}