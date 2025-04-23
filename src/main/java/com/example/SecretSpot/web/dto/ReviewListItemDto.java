package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewListItemDto {
    private Long id;
    private String profileImageUrl;
    private String nickname;
    private Byte rating;
    private String content;
    private LocalDateTime createdAt;
}