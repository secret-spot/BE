package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.*;
import com.example.SecretSpot.mapper.GuideMapper;
import com.example.SecretSpot.repository.*;
import com.example.SecretSpot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    private final ScrapService scrapService;
    private final GuideMapper guideMapper;
    private final RankingService rankingService;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
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
                        .googleId(placeDto.getGooglePlaceId())
                        .reviewNum(placeDto.getReviewNum())
                        .build());
            }
            else {
                place.setReviewNum(placeDto.getReviewNum());
            }
            guidePlaceRepository.save(GuidePlace.builder().guide(savedGuide).place(place).build());
        }

        for (String image : guide.getImages()) {
            //System.out.println("image = " + image);
            //System.out.println("guideImageRepository.existsByGuideAndUrl(savedGuide, image = " + guideImageRepository.existsByGuideAndUrl(savedGuide, image));
            if (!guideImageRepository.existsByGuideAndUrl(savedGuide, image)) {
                guideImageRepository.save(GuideImage.builder().guide(savedGuide).url(image).sortOrder(order++).build());
            }
        }

        rankingService.setPoint(user, guide.getPlaces());
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
        return guideMapper.toListDtos(hiddenGuide);
    }

    // 가이드 상세 조회 페이지
    public DetailedGuideDto getDetailedGuide(Long id, User user) {
        Guide detailedGuide = guideRepository.findById(id).orElseThrow(() -> new RuntimeException(id + "가이드 없음"));
        List<String> guideImages = new ArrayList<>();

        List<GuideImage> images = guideImageRepository.findByGuide_Id(id);
        images.sort(Comparator.comparing(GuideImage::getSortOrder));
        for(GuideImage image : images) {
            guideImages.add(image.getUrl());
        }

        List<String> keywords = guideKeywordRepository.findByGuide_Id(id).stream()
                .map(guideKeyword -> guideKeyword.getKeyword().getName()).toList();
        List<PlaceDto> guidePlaces = guidePlaceRepository.findByGuide_Id(id).stream()
                .map(guidePlace -> {
                    return PlaceDto.builder().googlePlaceId(guidePlace.getPlace().getGoogleId())
                            .address(guidePlace.getPlace().getAddress())
                            .name(guidePlace.getPlace().getName()).build();
                }).toList();
        List<RegionDto> regions = guideRegionRepository.findByGuide_Id(id).stream()
                .map(guideRegion -> {
                    return RegionDto.builder().region(guideRegion.getRegion().getName())
                            .country(guideRegion.getRegion().getCountry()).build();
                }).toList();

        Boolean isMyGuide = (detailedGuide.getUser().getId().equals(user.getId())) ? Boolean.TRUE : Boolean.FALSE;
        Boolean isScraped = scrapRepository.existsByUser_IdAndGuide_Id(user.getId(), id);
        return DetailedGuideDto.builder().images(guideImages).content(detailedGuide.getContent()).startDate(detailedGuide.getStartDate())
                .endDate(detailedGuide.getEndDate()).title(detailedGuide.getTitle()).places(guidePlaces).keywords(keywords).regions(regions)
                .reviewRating(String.format("%.1f", detailedGuide.getReviewRating())).isMyGuide(isMyGuide).isScraped(isScraped).build();
    }
}
