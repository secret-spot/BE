package com.example.SecretSpot.service;

import com.example.SecretSpot.common.util.UserUtils;
import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Review;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.event.review.ReviewSummaryEvent;
import com.example.SecretSpot.provider.GuideThumbnailProvider;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.ReviewRepository;
import com.example.SecretSpot.web.dto.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final GuideRepository guideRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final GuideThumbnailProvider guideThumbnailProvider;
    private final GuideRegionService guideRegionService;

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

    /**
     * 특정 가이드의 리뷰 탭 데이터 반환 함수
     */
    public GuideReviewTabDto getGuideReviewTab(Long guideId, User user) {
        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Guide, id=" + guideId));

        String summaryReview = guide.getSummaryReview();

        MyReviewDto myReviewDto = null;
        String myReviewStatus = "NOT_WRITTEN";
        if (!guide.getUser().getId().equals(user.getId())) {
            Review myReview = reviewRepository.findByGuideIdAndUser(guideId, user).orElse(null);
            if (myReview != null) {
                myReviewDto = MyReviewDto.builder()
                        .id(myReview.getId())
                        .rating(myReview.getRating())
                        .content(myReview.getContent())
                        .createdAt(myReview.getCreatedAt())
                        .build();
                myReviewStatus = "WRITTEN";
            }
        } else {
            myReviewStatus = "WRITER";
        }

        List<Review> reviewList = reviewRepository.findAllByGuideIdOrderByCreatedAtDesc(guideId);

        List<ReviewListItemDto> reviews = reviewList.stream()
                .map(review -> ReviewListItemDto.builder()
                        .id(review.getId())
                        .profileImageUrl(review.getUser().getProfileImageUrl())
                        .nickname(UserUtils.getNicknameOrName(review.getUser()))
                        .rating(review.getRating())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .build()
                )
                .collect(Collectors.toList());

        return GuideReviewTabDto.builder()
                .myReviewStatus(myReviewStatus)
                .myReview(myReviewDto)
                .summaryReview(summaryReview)
                .reviews(reviews)
                .build();
    }

    /**
     * 마이페이지의 내가 작성한 리뷰 카드뷰 리스트 데이터 반환 함수
     */
    public List<MyReviewCardDto> getMyReviews(User user) {
        List<Review> reviews = reviewRepository.findAllByUserId(user.getId());

        List<Long> guideIds = reviews.stream()
                .map(review -> review.getGuide().getId())
                .collect(Collectors.toList());

        Map<Long, List<String>> guideRegions = guideRegionService.getGuideRegionNames(guideIds);

        return reviews.stream()
                .map(review -> {
                    Guide guide = review.getGuide();

                    return MyReviewCardDto.builder()
                            .guideId(guide.getId())
                            .guideThumbnailUrl(guideThumbnailProvider.getThumbnailUrl(guide.getId()))
                            .guideTitle(guide.getTitle())
                            .guideRegion(guideRegions.get(guide.getId()))
                            .myReviewRating(review.getRating())
                            .build();
                })
                .collect(Collectors.toList());
    }
}