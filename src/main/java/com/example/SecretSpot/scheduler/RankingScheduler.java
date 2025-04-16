package com.example.SecretSpot.scheduler;

import com.example.SecretSpot.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankingScheduler {

    private final RankingService rankingService;

    /**
     * 한국 시간 기준 매주 월요일 00시에 전체 유저 랭킹 업데이트
     */
    @Scheduled(cron = "0 0 0 * * MON", zone = "Asia/Seoul")
    public void updateWeeklyRankings() {
        rankingService.updateRankings();
    }
}
