package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Ranking;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.RankingRepository;
import com.example.SecretSpot.repository.ReviewRepository;
import com.example.SecretSpot.repository.UserKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final GuideRepository guideRepository;
    private final RankingRepository rankingRepository;
    private final ReviewRepository reviewRepository;
    private final UserKeywordRepository userKeywordRepository;

    public Map<String, Object> getUserProfile(User user) {
        Long userId = user.getId();
        Ranking ranking = rankingRepository.findById(userId).orElseThrow(() -> new RuntimeException("랭킹 정보 없음"));
        return Map.of("profile_image", user.getProfileImageUrl(),
                "name", user.getName(),
                "nickname", user.getNickname(),
                "keyword", userKeywordRepository.findByUserId(userId),
                "ranking", ranking.getRanking(),
                "point", ranking.getTotalPoint(),
                "userGuides", guideRepository.findByUserId(userId),
                "userReviews", reviewRepository.findByUserId(userId)
        );
    }
}
