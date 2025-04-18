package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.*;
import com.example.SecretSpot.repository.*;
import com.example.SecretSpot.web.dto.GuideDto;
import com.example.SecretSpot.web.dto.GuideListItemDto;
import com.example.SecretSpot.web.dto.PlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideService {
    private final GuideRepository guideRepository;
    private final GuideImageRepository guideImageRepository;
    private final GuidePlaceRepository guidePlaceRepository;
    private final PlaceRepository placeRepository;
    private final GuideRegionRepository guideRegionRepository;
    private final GuideKeywordRepository guideKeywordRepository;
    private final RegionService regionService;
    private final KeywordService keywordService;
    private final GuideKeywordService guideKeywordService;
    private final GuideRegionService guideRegionService;
    int order = 1;

    public Long saveGuide(GuideDto guide, User user) {
        // Guide 저장
        LocalDate startDate = guide.getStartDate();
        LocalDate endDate = guide.getEndDate();
        Guide savedGuide = guideRepository.save(Guide.builder().title(guide.getTitle()).user(user)
                .content(guide.getContent()).startDate(startDate)
                .endDate(endDate).build());
        guideRepository.flush();

        // GuidePlace 저장
        for (PlaceDto placeDto : guide.getPlaces()) {
            Place place = placeRepository.findByGoogleId(placeDto.getGooglePlaceId());
            if (place == null) {
                place = placeRepository.save(Place.builder().name(placeDto.getName())
                        .address(placeDto.getAddress())
                        .googleId(placeDto.getGooglePlaceId()).build());
            }
            guidePlaceRepository.save(GuidePlace.builder().guide(savedGuide).place(place).build());
        }

        for (String image : guide.getImages()) {
            System.out.println("image = " + image);
            System.out.println("guideImageRepository.existsByGuideAndUrl(savedGuide, image = " + guideImageRepository.existsByGuideAndUrl(savedGuide, image));
            if (!guideImageRepository.existsByGuideAndUrl(savedGuide, image)) {
                guideImageRepository.save(GuideImage.builder().guide(savedGuide).url(image).sortOrder(order++).build());
            }
        }
        order = 1;

        return savedGuide.getId();
    }

    public void analyzeGuide(Long id) {
        Guide guide = guideRepository.findById(id).orElseThrow(() -> new RuntimeException("가이드 없음"));
        /**
         // FastAPI 호출
         AnalyzeResponseDto aiResponse;

         // 테스트용
         List<String> aiKeywords = aiResponse.getKeywords();
         List<RegionDto> aiRegions = aiResponse.getRegions();

         List<Keyword> keywords = keywordService.getKeywords(aiKeywords);
         List<Region> regions = regionService.getRegions(aiRegions);

         for (Keyword keyword : keywords) {
         if (!guideKeywordRepository.existsByGuideAndKeyword(guide, keyword)) {
         guideKeywordRepository.save(GuideKeyword.builder().guide(guide).keyword(keyword).build());
         }
         }
         for (Region region : regions) {
         if (!guideRegionRepository.existsByGuideAndRegion(guide, region)) {
         guideRegionRepository.save(GuideRegion.builder().guide(guide).region(region).build());
         }
         } **/
    }

    /**
     * 탐색 페이지 초기 화면에 띄울 가이드 불러오는 함수
     */
    public List<GuideListItemDto> getHiddenGuide() {
        List<Guide> hiddenGuide = guideRepository.findTop10ByOrderByRarityPointDesc();
        return convertToGuideListItemDtos(hiddenGuide);
    }

    /**
     * 썸네일 반환 함수
     */
    private String getThumbnailUrl(Long guideId) {
        GuideImage image = guideImageRepository.findTop1ByGuideIdOrderBySortOrderAsc(guideId);
        return image != null ? image.getUrl() : null;
    }

    /**
     * List<Guide>를 GuideListItemDto로 변환하는 함수
     */
    private List<GuideListItemDto> convertToGuideListItemDtos(List<Guide> guides) {
        List<Long> guideIds = guides.stream()
                .map(Guide::getId)
                .collect(Collectors.toList());

        Map<Long, List<String>> guideKeywords = guideKeywordService.getGuideKeywordNames(guideIds);
        Map<Long, List<String>> guideRegions = guideRegionService.getGuideRegionNames(guideIds);

        return guides.stream()
                .map(guide -> GuideListItemDto.builder()
                        .id(guide.getId())
                        .thumbnailUrl(getThumbnailUrl(guide.getId()))
                        .title(guide.getTitle())
                        .reviewRating(guide.getReviewRating())
                        .keywords(guideKeywords.getOrDefault(guide.getId(), Collections.emptyList()))
                        .regions(guideRegions.getOrDefault(guide.getId(), Collections.emptyList()))
                        .build())
                .collect(Collectors.toList());
    }
}
