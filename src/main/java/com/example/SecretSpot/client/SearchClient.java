package com.example.SecretSpot.client;

import com.example.SecretSpot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchClient {

    private final WebClient webClient;

    /**
     * 검색어가 지역명인지 구분하는 API
     */
    public CheckIsRegionItemDto checkIsRegion(String prompt) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/search/")
                        .queryParam("prompt", prompt)
                        .build())
                .retrieve()
                .bodyToMono(CheckIsRegionResponseDto.class)
                .map(CheckIsRegionResponseDto::getResult)
                .block();
    }


    /**
     * 검색어가 지역명일 경우 그 지역의 에티켓을 생성하는 API
     */
    public String createEtiquette(String prompt) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/etiquette/")
                        .queryParam("prompt", prompt)
                        .build())
                .retrieve()
                .bodyToMono(CreateEtiquetteResponseDto.class)
                .map(CreateEtiquetteResponseDto::getContent)
                .block();
    }

    /**
     * 근처의 소도시를 추천하는 API
     */
    public List<NearbyRegionItemDto> recommendNearbyCities(String prompt) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/recommend/")
                        .queryParam("prompt", prompt)
                        .build())
                .retrieve()
                .bodyToMono(NearbyRegionRecommendResponseDto.class)
                .map(NearbyRegionRecommendResponseDto::getRecommendations)
                .block();
    }
}
