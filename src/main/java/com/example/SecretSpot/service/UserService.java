package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Keyword;
import com.example.SecretSpot.domain.Ranking;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.domain.UserKeyword;
import com.example.SecretSpot.domain.compositekeys.UserKeywordId;
import com.example.SecretSpot.repository.*;
import com.example.SecretSpot.web.dto.ProfileUpdateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final GuideRepository guideRepository;
    private final RankingRepository rankingRepository;
    private final ReviewRepository reviewRepository;
    private final UserKeywordRepository userKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public void updateUserProfile(ProfileUpdateRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) { throw new RuntimeException("업데이트할 유저를 찾을 수 없음"); }
        if (requestDto.getNickname() != null) {
            user.setNickname(requestDto.getNickname());
        }
        if (requestDto.getProfileImageUrl() != null) {
            user.setProfileImageUrl(requestDto.getProfileImageUrl());
        }
        if (requestDto.getKeywords() != null) {
            userKeywordRepository.deleteByUserId(user.getId());

            for (String keywordName : requestDto.getKeywords()) {
                Keyword keyword = keywordRepository.findByName(keywordName)
                        .orElseThrow(() -> new RuntimeException("잘못된 키워드"));
                UserKeyword userKeyword = new UserKeyword();
                userKeyword.setId(new UserKeywordId(keyword.getId(), user.getId()));
                userKeyword.setUser(user);
                userKeyword.setKeyword(keyword);
                userKeywordRepository.save(userKeyword);
            }
        }
    }
}
