package com.example.SecretSpot.service;

import com.example.SecretSpot.common.util.UserUtils;
import com.example.SecretSpot.domain.*;
import com.example.SecretSpot.domain.compositekeys.UserKeywordId;
import com.example.SecretSpot.mapper.GuideMapper;
import com.example.SecretSpot.repository.*;
import com.example.SecretSpot.web.dto.GuideCardItemDto;
import com.example.SecretSpot.web.dto.ProfileUpdateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final GuideRepository guideRepository;
    private final UserKeywordRepository userKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final UserRepository userRepository;
    private final RankingService rankingService;
    private final ReviewService reviewService;
    private final GuideMapper guideMapper;
    private final ImageService imageService;

    public Map<String, Object> getUserProfile(User user) {
        Long userId = user.getId();
        Ranking ranking = rankingService.getMyRanking(user);
        return Map.of("profile_image", user.getProfileImageUrl(),
                "name", user.getName(),
                "nickname", UserUtils.getNicknameOrName(user),
                "keyword", userKeywordRepository.findByUserId(userId).stream()
                        .map(userKeyword -> userKeyword.getKeyword().getName()).collect(Collectors.toList()),
                "ranking", ranking.getRanking(),
                "point", ranking.getTotalPoint(),
                "userGuides", Optional.ofNullable(guideMapper.toCardDtosWithoutScrap(guideRepository.findTop3ByUserIdOrderByCreatedAtDesc(userId))).orElse(Collections.emptyList()),
                "userReviews", Optional.ofNullable(reviewService.getMyReviews(user)).orElse(Collections.emptyList())
        );
    }

    @Transactional
    public void updateUserProfile(ProfileUpdateRequestDto requestDto, MultipartFile file, String email) throws IOException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("업데이트할 유저를 찾을 수 없음");
        }
        if (requestDto != null) {
            if (requestDto.getNickname() != null) {
                user.setNickname(requestDto.getNickname());
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
        if (file != null && !file.isEmpty()) {
            String imageUrl = imageService.uploadImage(file);
            user.setProfileImageUrl(imageUrl);
        }
    }

    public List<GuideCardItemDto> getUserGuides(User user) {
        List<Guide> userGuides = guideRepository.findByUser(user);
        return guideMapper.toCardDtosWithoutScrap(userGuides);
    }
}
