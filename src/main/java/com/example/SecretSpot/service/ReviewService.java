package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Review;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.event.review.ReviewSummaryEvent;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.ReviewRepository;
import com.example.SecretSpot.web.dto.ReviewCreateDto;
import com.example.SecretSpot.web.dto.ReviewCreateResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final GuideRepository guideRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 리뷰 작성 함수
     */
    @Transactional
    public ReviewCreateResponseDto createReview(
            Long guideId,
            ReviewCreateDto reviewCreateDto,
            User user
    ) {
        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Guide, id=" + guideId));

        if (guide.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인의 가이드에는 리뷰를 작성할 수 없습니다.");
        }

        boolean alreadyReviewed = reviewRepository.existsByGuideIdAndUserId(guide.getId(), user.getId());
        if (alreadyReviewed) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 이 가이드에 리뷰를 작성하셨습니다.");
        }

        Review review = Review.builder()
                .guide(guide)
                .user(user)
                .content(reviewCreateDto.getContent())
                .rating(reviewCreateDto.getRating())
                .build();

        reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findAllByGuideId(guideId);

        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        int totalCount = reviews.size();

        if (totalCount >= 5) {
            eventPublisher.publishEvent(new ReviewSummaryEvent(guideId));
        }

        guide.setReviewRating(averageRating);
        guideRepository.save(guide);

        return ReviewCreateResponseDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .totalReviewCount(totalCount)
                .averageRating(averageRating)
                .build();
    }
}