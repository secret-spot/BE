package com.example.SecretSpot.service;

import com.example.SecretSpot.common.util.UserUtils;
import com.example.SecretSpot.domain.Ranking;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.repository.GuideRepository;
import com.example.SecretSpot.repository.RankingRepository;
import com.example.SecretSpot.repository.ReviewRepository;
import com.example.SecretSpot.repository.UserRepository;
import com.example.SecretSpot.web.dto.HomeRankingDto;
import com.example.SecretSpot.web.dto.RankingPageDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingRepository rankingRepository;
    private final UserKeywordService userKeywordService;
    private final GuideRepository guideRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    /**
     * 랭킹 업데이트 함수
     */
    @Transactional
    public void updateRankings() {

        List<User> users = userRepository.findAll();
        List<Ranking> newRankings = new ArrayList<>();

        for (User user : users) {
            Long userId = user.getId();

            Integer guidePoint = guideRepository.countByUserId(userId);
            Integer reviewPoint = reviewRepository.countByUserId(userId);
            Integer rarityPoint = Optional.ofNullable(guideRepository.sumRarityPointByUserId(userId)).orElse(0);
            Integer guideRatingPoint = guideRepository.countByUserIdAndReviewRatingGreaterThanEqual(userId, 4.0);

            int totalPoint = guidePoint + reviewPoint + rarityPoint + guideRatingPoint;

            Ranking ranking = Ranking.builder()
                    .user(user)
                    .guidePoints(guidePoint)
                    .reviewPoint(reviewPoint)
                    .rarityPoint(rarityPoint)
                    .guideRatingPoint(guideRatingPoint)
                    .totalPoint(totalPoint)
                    .build();
            newRankings.add(ranking);
        }

        newRankings.sort((r1, r2) -> Integer.compare(r2.getTotalPoint(), r1.getTotalPoint()));

        int rank = 1;
        for (Ranking ranking : newRankings) {
            ranking.setRanking((long) rank++);
        }

        rankingRepository.saveAll(newRankings);
    }

    /**
     * 홈의 내 랭킹 섹션에 띄울 유저 반환 함수
     */
    public List<HomeRankingDto> getHomeRanking() {
        List<Ranking> topRankings = getTopRankings(4);

        return topRankings.stream()
                .map(ranking -> {
                    User user = ranking.getUser();
                    String nickname = UserUtils.getNicknameOrName(user);
                    return HomeRankingDto.builder()
                            .rank(ranking.getRanking().intValue())
                            .nickname(nickname)
                            .profileImageUrl(user.getProfileImageUrl())
                            .build();
                })
                .toList();
    }

    /**
     * 랭킹 페이지에 필요한 데이터 반환 함수
     */
    public List<RankingPageDto> getRankingPage(User me) {
        Ranking myRanking = getMyRanking(me);
        List<Ranking> topRankings = getTopRankings(10);

        //유저 키워드 매핑용 userId 리스트 생성 후 키워드 매핑
        List<Long> userIds = Stream.concat(
                Stream.of(me.getId()),
                topRankings.stream().map(r -> r.getUser().getId())
        ).collect(Collectors.toList());
        Map<Long, List<String>> userKeywords = userKeywordService.getUserKeywordNames(userIds);

        Function<Ranking, RankingPageDto> convertRankingToPageDto = ranking -> {
            User user = ranking.getUser();
            String nickname = UserUtils.getNicknameOrName(user);
            String profileImageUrl = user.getProfileImageUrl();
            return RankingPageDto.builder()
                    .rank(ranking.getRanking().intValue())
                    .nickname(nickname)
                    .profileImageUrl(profileImageUrl)
                    .keywords(userKeywords.getOrDefault(user.getId(), Collections.emptyList()))
                    .build();
        };

        List<Ranking> resultRankings = Stream.concat(
                Stream.of(myRanking),
                topRankings.stream()
        ).toList();

        return resultRankings.stream()
                .map(convertRankingToPageDto)
                .toList();
    }

    /**
     * 나의 최신 랭킹 반환 함수
     */
    public Ranking getMyRanking(User user) {
        return rankingRepository
                .findTop1ByUserIdOrderByCreatedAtDesc(user.getId())
                .orElseGet(() -> {
                    Ranking empty = new Ranking();
                    empty.setUser(user);
                    empty.setRanking(0L);
                    return empty;
                });
    }

    /**
     * 최신 날짜 기준 랭킹 상위 n명 계산 함수
     */
    private List<Ranking> getTopRankings(int count) {
        Pageable page = PageRequest.of(0, count, Sort.by("ranking"));
        return rankingRepository.findLatestSnapshotRankings(page);
    }
}