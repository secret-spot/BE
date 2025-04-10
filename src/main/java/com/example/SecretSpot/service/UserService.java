package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
//    private final GuideRepository guideRepository;
//    private final RankingRepository rankingRepository;
//    private final ReviewRepository reviewRepository;
//    private final KeyWordRepository keyWordRepository;

    public Map<String, Object> getUserProfile(User user) {
        //Long userId = user.getId();
        // guide, review, keyword Repo에서 findByUserId(Long userId) List 반환하도록
//        return Map.of("profile_image", user.getPicture(),
//                "name", user.getName(),
//                "nickname", user.getNickname(),
//                "keyword", keyWordRepository.findByUserId(userId),
//                "ranking", rankingRepository.findByUserId(userId).getRanking(),
//                "point", rankingRepository.findByUserId(userId).getTotalPoint(),
//                "userGuides", guideRepository.findByUserId(userId),
//                "userReviews", reviewRepository.findByUserId(userId)
//        );
        return null;
    }
}
