package com.example.SecretSpot.event.review;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Review;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.ReviewRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewSummaryEventListener {
    private final ReviewRepository reviewRepository;
    private final GuideRepository guideRepository;

    public ReviewSummaryEventListener(ReviewRepository reviewRepository, GuideRepository guideRepository) {
        this.reviewRepository = reviewRepository;
        this.guideRepository = guideRepository;
    }

    @Async
    @EventListener
    public void handleReviewSummary(ReviewSummaryEvent event) {
        Long guideId = event.guideId();

        List<String> contents = reviewRepository.findAllByGuideId(guideId)
                .stream()
                .map(Review::getContent)
                .toList();

//        String summary = summarizationService.summarize(contents);
        String summary = "이벤트 발생 여부 테스트용";

        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found Guide, id=" + guideId));

        guide.setSummaryReview(summary);

        guideRepository.save(guide);
    }
}