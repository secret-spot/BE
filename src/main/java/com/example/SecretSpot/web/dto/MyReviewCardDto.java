package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// 마이페이지에서 내 리뷰를 보여줄 때 사용하는 DTO
@Getter
@Builder
public class MyReviewCardDto {
    private Long guideId;
    private String guideThumbnailUrl;
    private List<String> guideRegion;
    private String guideTitle;
    private Byte myReviewRating;
}