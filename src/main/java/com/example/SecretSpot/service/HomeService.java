package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.*;
import com.example.SecretSpot.repository.GuideKeywordRepository;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.UserKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final UserKeywordRepository keywordRepository;
    private final GuideRepository guideRepository;
    private final GuideKeywordRepository guideKeywordRepository;

    public List<Guide> getLatestGuide() {
        return guideRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public List<Guide> getRecommendedGuide(User user) {
        // 유저키워드 랜덤으로 뽑아서 가이드 가져오기
        List<UserKeyword> userKeywords = keywordRepository.findByUserId(user.getId());
        Random random = new Random();
        int index = random.nextInt(userKeywords.size());
        UserKeyword selectedUserKeyword = userKeywords.get(index);
        Keyword selectedKeyword = selectedUserKeyword.getKeyword();

        // 개선 필요
        List<Guide> guides = guideKeywordRepository.findByKeyword(selectedKeyword);
        Collections.shuffle(guides);
        List<Guide> recommendedGuides = guides.stream().limit(5).collect(Collectors.toList());
        return recommendedGuides;
    }

    public List<Region> getRecommendedRegion() {
        // 로직 추가 필요
        return List.of(null);
    }
}