package com.example.SecretSpot.client;

import com.example.SecretSpot.web.dto.AI.*;
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
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/search/")
                        .build())
                .bodyValue(new promptRequestDto(prompt))
                .retrieve()
                .bodyToMono(CheckIsRegionResponseDto.class)
                .map(CheckIsRegionResponseDto::getResult)
                .block();
    }


    /**
     * 지역명/장소명을 전달하면 그 지역의 에티켓을 생성하는 API
     */
    public String createEtiquette(String prompt) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/etiquette/")
                        .build())
                .bodyValue(new promptRequestDto(prompt))
                .retrieve()
                .bodyToMono(CreateEtiquetteResponseDto.class)
                .map(CreateEtiquetteResponseDto::getContent)
                .block();
    }

    /**
     * 근처의 소도시를 추천하는 API
     */
    public List<RegionRecommendItemDto> recommendNearbyCities(String prompt) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/recommend/")
                        .build())
                .bodyValue(new promptRequestDto(prompt))
                .retrieve()
                .bodyToMono(NearbyRegionRecommendResponseDto.class)
                .map(NearbyRegionRecommendResponseDto::getRecommendations)
                .bodyToMono(RegionRecommendResponseDto.class)
                .map(RegionRecommendResponseDto::getRecommendations)
                .block();
    }
                .block();
    }
}
