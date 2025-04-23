package com.example.SecretSpot.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GuideReviewTabDto {
    private String myReviewStatus;
    private MyReviewDto myReview;
    private String summaryReview;
    private List<ReviewListItemDto> reviews;
}