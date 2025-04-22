package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.service.ReviewService;
import com.example.SecretSpot.web.dto.ReviewCreateDto;
import com.example.SecretSpot.web.dto.ReviewCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
