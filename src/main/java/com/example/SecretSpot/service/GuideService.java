package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.*;
import com.example.SecretSpot.repository.*;
import com.example.SecretSpot.web.dto.GuideDto;
import com.example.SecretSpot.web.dto.PlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    int order = 1;

    public Long saveGuide(GuideDto guide, User user) {
        // Guide 저장
        LocalDate startDate = guide.getStartDate();
        LocalDate endDate = guide.getEndDate();
        Long duration = ChronoUnit.DAYS.between(startDate, endDate);
        Guide savedGuide = guideRepository.save(Guide.builder().title(guide.getTitle()).user(user)
                .content(guide.getContent()).startDate(startDate)
                .endDate(endDate).durationDays(duration).build());
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
}
