package com.example.SecretSpot.mapper;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.service.GuideImageService;
import com.example.SecretSpot.service.GuideKeywordService;
import com.example.SecretSpot.service.GuideRegionService;
import com.example.SecretSpot.web.dto.GuideCardItemDto;
import com.example.SecretSpot.web.dto.GuideListItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GuideMapper {
    private final GuideKeywordService guideKeywordService;
    private final GuideRegionService guideRegionService;
    private final GuideImageService guideImageService;

    /**
     * 스크랩 기능이 있는 가이드 카드 뷰 전용 DTO 변환
     */
    public List<GuideCardItemDto> toCardDtos(
            List<Guide> guides, Set<Long> scrapedIds
    ) {
        List<Long> guideIds = guides.stream().map(Guide::getId).toList();
        Map<Long, List<String>> keywords = guideKeywordService.getGuideKeywordNames(guideIds);
        Map<Long, List<String>> regions = guideRegionService.getGuideRegionNames(guideIds);

        return guides.stream().map(guide -> {
            Long guideId = guide.getId();

            Boolean isScraped = null;
            if (scrapedIds != null) {
                isScraped = scrapedIds.contains(guideId);
            }

            return GuideCardItemDto.builder()
                    .id(guideId)
                    .thumbnailUrl(guideImageService.getThumbnailUrl(guideId))
                    .title(guide.getTitle())
                    .keywords(keywords.getOrDefault(guideId, List.of()))
                    .regions(regions.getOrDefault(guideId, List.of()))
                    .isScraped(isScraped)
                    .build();
        }).toList();
    }

    /**
     * 스크랩 기능이 없는 가이드 카드 뷰 전용 DTO 변환
     */
    public List<GuideCardItemDto> toCardDtos(List<Guide> guides) {
        return toCardDtos(guides, null);
    }

    /**
     * 가이드 리스트 뷰 전용 DTO 변환
     */
    public List<GuideListItemDto> toListDtos(
            List<Guide> guides
    ) {
        List<Long> guideIds = guides.stream().map(Guide::getId).toList();
        Map<Long, List<String>> keywords = guideKeywordService.getGuideKeywordNames(guideIds);
        Map<Long, List<String>> regions = guideRegionService.getGuideRegionNames(guideIds);

        return guides.stream().map(guide -> {
            Long guideId = guide.getId();
            return GuideListItemDto.builder()
                    .id(guideId)
                    .thumbnailUrl(guideImageService.getThumbnailUrl(guideId))
                    .title(guide.getTitle())
                    .reviewRating(guide.getReviewRating())
                    .keywords(keywords.getOrDefault(guideId, List.of()))
                    .regions(regions.getOrDefault(guideId, List.of()))
                    .build();
        }).toList();
    }
}
