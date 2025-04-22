package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 리뷰 탭에서 내 리뷰를 보여줄 때 사용하는 DTO
@Getter
@Builder
public class MyReviewDto {
    private Long id;
    private Byte rating;
    private String content;
    private LocalDateTime createdAt;
}