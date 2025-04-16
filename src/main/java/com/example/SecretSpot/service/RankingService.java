package com.example.SecretSpot.service;

import com.example.SecretSpot.common.util.UserUtils;
import com.example.SecretSpot.domain.Ranking;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.repository.RankingRepository;
import com.example.SecretSpot.web.dto.HomeRankingDto;
import com.example.SecretSpot.web.dto.RankingPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingRepository rankingRepository;
    private final UserKeywordService userKeywordService;

    //홈의 내 랭킹 섹션에 띄울 유저 반환 함수
    public List<HomeRankingDto> getHomeRanking() {
        List<Ranking> topRankings = getTopRankings();

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

    public List<RankingPageDto> getRankingPage(User me) {
        Ranking myRanking = getMyRanking(me.getId());
        List<Ranking> topRankings = getTopRankings();

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

    //나의 최신 랭킹 반환 함수
    public Ranking getMyRanking(Long userId) {
        return rankingRepository.findTop1ByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new IllegalArgumentException("랭킹 값이 존재하지 않는 유저입니다."));
    }

    //최신 날짜 기준 랭킹 상위 5명 계산 함수
    private List<Ranking> getTopRankings() {
        return rankingRepository.findTop5ByOrderByCreatedAtDescRankingAsc();
    }
}