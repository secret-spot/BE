package com.example.SecretSpot.event.review;

import com.example.SecretSpot.client.ReviewClient;
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
    private final ReviewClient reviewClient;

    public ReviewSummaryEventListener(ReviewRepository reviewRepository, GuideRepository guideRepository, ReviewClient reviewClient) {
        this.reviewRepository = reviewRepository;
        this.guideRepository = guideRepository;
        this.reviewClient = reviewClient;
    }

    @Async
    @EventListener
    public void handleReviewSummary(ReviewSummaryEvent event) {
        Long guideId = event.guideId();

        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found Guide, id=" + guideId));

        List<String> contents = reviewRepository.findAllByGuideId(guideId)
                .stream()
                .map(Review::getContent)
                .toList();

        String prompt = String.join(",", contents);

        reviewClient.summarizeReview(prompt)
                .subscribe(
                        summary -> {
                            guide.setSummaryReview(summary);
                            guideRepository.save(guide);
                        },
                        err -> {
                            System.err.println("요약 API 호출 실패: " + err.getMessage());
                        }
                );
    }
}