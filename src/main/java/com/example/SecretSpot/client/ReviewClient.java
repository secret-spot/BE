package com.example.SecretSpot.client;

import com.example.SecretSpot.web.dto.ReviewSummaryDto;
import com.example.SecretSpot.web.dto.ReviewSummaryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReviewClient {

    private final WebClient webClient;

    /**
     * 리뷰 요약 AI API
     */
    public Mono<String> summarizeReview(String prompt) {
        return webClient.post()
                .uri("/api/v1/summary/")
                .bodyValue(new ReviewSummaryDto(prompt))
                .retrieve()
                .bodyToMono(ReviewSummaryResponseDto.class)
                .map(ReviewSummaryResponseDto::getContent);
    }
}
