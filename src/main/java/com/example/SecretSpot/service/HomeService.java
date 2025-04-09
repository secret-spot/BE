package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Region;
import com.example.SecretSpot.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    GuideRepository guideRepository;

    public List<Guide> getLatestGuide() {
        return guideRepository.findTop5ByOrderByCreatedDateDesc();
    }

    public List<Guide> getRecommendedGuide() {
        // 로직 추가 필요
        return List.of(null);
    }

    public List<Region> getRecommendedRegion() {
        // 로직 추가 필요
        return List.of(null);
    }
}
