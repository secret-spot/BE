package com.example.SecretSpot.service;

import com.example.SecretSpot.common.util.UserUtils;
import com.example.SecretSpot.domain.*;
import com.example.SecretSpot.mapper.GuideMapper;
import com.example.SecretSpot.repository.*;
import com.example.SecretSpot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

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
    private final GuideMapper guideMapper;
    private final ScrapRepository scrapRepository;
    private final ImageService imageService;
    private final RegionKeywordRepository regionKeywordRepository;
    private final String API_URL = "https://secret-spot-456800.du.r.appspot.com/api/v1/keywords/";
    int order = 1;

    public Long saveGuide(GuideDto guide, List<MultipartFile> images, User user) throws IOException {
        // Guide 저장
        LocalDate startDate = guide.getStartDate();
        LocalDate endDate = guide.getEndDate();
        Guide savedGuide = guideRepository.save(
                Guide.builder()
                        .title(guide.getTitle())
                        .user(user)
                        .content(guide.getContent())
                        .startDate(startDate)
                        .endDate(endDate)
                        .rarityPoint(calculateRarityPoint(guide.getPlaces()))
                        .build());
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
            } else {
                place.setReviewNum(placeDto.getReviewNum());
            }
            guidePlaceRepository.save(GuidePlace.builder().guide(savedGuide).place(place).build());
        }

        for (MultipartFile image : images) {
            String imageUrl = imageService.uploadImage(image);
            if (!guideImageRepository.existsByGuideAndUrl(savedGuide, imageUrl)) {
                guideImageRepository.save(GuideImage.builder().guide(savedGuide).url(imageUrl).sortOrder(order++).build());
            }
        }

        order = 1;

        return savedGuide.getId();
    }

    public void analyzeGuide(Long id) {
        Guide guide = guideRepository.findById(id).orElseThrow(() -> new RuntimeException("가이드 없음"));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("prompt", guide.getContent());
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // FastAPI 호출
        AnalyzeResponseDto aiResponse = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, AnalyzeResponseDto.class).getBody();
        System.out.println("aiResponse = " + aiResponse);

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
            for (Keyword keyword : keywords) {
                if (!regionKeywordRepository.existsByRegionAndKeyword(region, keyword)) {
                    System.out.println("keyword for문 진입");
                    regionKeywordRepository.save(RegionKeyword.builder().keyword(keyword).region(region).build());
                }
            }
        }
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
        for (GuideImage image : images) {
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

        User writer = detailedGuide.getUser();

        Boolean isMyGuide = (writer.getId().equals(user.getId())) ? Boolean.TRUE : Boolean.FALSE;
        Boolean isScraped = scrapRepository.existsByUser_IdAndGuide_Id(user.getId(), id);

        return DetailedGuideDto.builder()
                .isMyGuide(isMyGuide)
                .isScraped(isScraped)
                .images(guideImages)
                .title(detailedGuide.getTitle())
                .userImage(writer.getProfileImageUrl())
                .userName(UserUtils.getNicknameOrName(writer))
                .startDate(detailedGuide.getStartDate())
                .endDate(detailedGuide.getEndDate())
                .keywords(keywords)
                .regions(regions)
                .reviewRating(String.format("%.1f", detailedGuide.getReviewRating()))
                .content(detailedGuide.getContent())
                .places(guidePlaces)
                .build();
    }

    public int calculateRarityPoint(List<PlaceDto> places) {
        int rarityPoint = 0;
        for (PlaceDto place : places) {
            Integer reviewNum = place.getReviewNum();
            if (reviewNum <= 100) {
                rarityPoint += 3;
            } else if (reviewNum <= 200) {
                rarityPoint += 2;
            } else if (reviewNum <= 300) {
                rarityPoint += 1;
            }
        }
        return rarityPoint;
    }
}
