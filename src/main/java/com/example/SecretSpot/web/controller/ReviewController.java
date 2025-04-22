package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.service.ReviewService;
import com.example.SecretSpot.web.dto.GuideReviewTabDto;
import com.example.SecretSpot.web.dto.MyReviewCardDto;
import com.example.SecretSpot.web.dto.ReviewCreateDto;
import com.example.SecretSpot.web.dto.ReviewCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/guides/{guideId}/reviews")
    public ResponseEntity<ReviewCreateResponseDto> createReview(@PathVariable("guideId") Long guideId,
                                                                @Validated @RequestBody ReviewCreateDto reviewCreateDto,
                                                                @AuthenticationPrincipal User user) {
        ReviewCreateResponseDto responseDto = reviewService.createReview(guideId, reviewCreateDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/api/guides/{guideId}/reviews")
    public ResponseEntity<GuideReviewTabDto> getGuideReviewTab(@PathVariable("guideId") Long guideId,
                                                               @AuthenticationPrincipal User user) {
        GuideReviewTabDto responseDto = reviewService.getGuideReviewTab(guideId, user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/api/mypage/reviews")
    public ResponseEntity<List<MyReviewCardDto>> getMyReviews(@AuthenticationPrincipal User user) {
        List<MyReviewCardDto> responseDto = reviewService.getMyReviews(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
