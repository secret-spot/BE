package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.service.RankingService;
import com.example.SecretSpot.web.dto.HomeRankingDto;
import com.example.SecretSpot.web.dto.RankingPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/api/rankings/home")
    public ResponseEntity<List<HomeRankingDto>> getHomeRanking() {
        List<HomeRankingDto> responseDto = rankingService.getHomeRanking();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/api/rankings")
    public ResponseEntity<List<RankingPageDto>> getRankingPage(@AuthenticationPrincipal User user) {
        List<RankingPageDto> responseDto = rankingService.getRankingPage(user);
        return ResponseEntity.ok(responseDto);
    }
}
