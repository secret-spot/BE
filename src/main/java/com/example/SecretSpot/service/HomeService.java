package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Region;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    GuideRepository guideRepository;

    public List<Guide> getLatestGuide() {
        return guideRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public List<Guide> getRecommendedGuide(User user) {
        // 유저키워드 랜덤으로 뽑아서 가이드 가져오기
        return List.of(null);
    }

    public List<Region> getRecommendedRegion() {
        // 로직 추가 필요
        return List.of(null);
    }
}