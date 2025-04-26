package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.*;
import com.example.SecretSpot.mapper.GuideMapper;
import com.example.SecretSpot.repository.*;
import com.example.SecretSpot.web.dto.GuideCardItemDto;
import com.example.SecretSpot.web.dto.RegionRecommendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final UserKeywordRepository keywordRepository;
    private final GuideRepository guideRepository;
    private final UserKeywordRepository userKeywordRepository;
    private final RegionKeywordRepository regionKeywordRepository;
    private final GuideMapper guideMapper;

    public List<GuideCardItemDto> getLatestGuide() {
        List<Guide> latestGuides = guideRepository.findTop5ByOrderByCreatedAtDesc();
        return guideMapper.toCardDtosWithoutScrap(latestGuides);
    }

    public List<GuideCardItemDto> getRecommendedGuide(User user) {
        // 유저키워드 랜덤으로 뽑아서 가이드 가져오기
        List<Keyword> keywords = keywordRepository.findByUserId(user.getId()).stream().map(UserKeyword::getKeyword).toList();
        List<Guide> recommendedGuides = guideRepository.findGuidesByUserKeywords(keywords);
        Collections.shuffle(recommendedGuides);
        recommendedGuides = recommendedGuides.stream().limit(5).toList();
        return guideMapper.toCardDtosWithoutScrap(recommendedGuides);
    }

    public List<RegionRecommendDto> getRecommendedRegion(User user) {
        List<RegionRecommendDto> regionRecommendDtos = new ArrayList<>();

        List<Keyword> keywords = userKeywordRepository.findByUserId(user.getId()).stream().map(UserKeyword::getKeyword).toList();
        List<Region> regions = regionKeywordRepository.findRegionsByUserKeywordsOrderByMatchCount(keywords).stream().limit(5).toList();

        for(Region region : regions) {
            regionRecommendDtos.add(RegionRecommendDto.builder().region(region.getName())
                    .country(region.getCountry())
                    .keywords(regionKeywordRepository.findByRegion(region).stream().map(regionKeyword -> regionKeyword.getKeyword().getName()).toList())
                    .build());
        }
        return regionRecommendDtos;
    }
}