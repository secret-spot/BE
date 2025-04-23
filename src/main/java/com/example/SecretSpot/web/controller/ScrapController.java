package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.service.ScrapService;
import com.example.SecretSpot.web.dto.GuideCardItemDto;
import com.example.SecretSpot.web.dto.GuideListItemDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/api/guides/{guideId}/scraps")
    public ResponseEntity<Object> createScrap(@PathVariable Long guideId, @AuthenticationPrincipal User user) throws BadRequestException {
        scrapService.createScrap(guideId, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/api/guides/{guideId}/scraps")
    public ResponseEntity<Object> deleteScrap(@PathVariable Long guideId, @AuthenticationPrincipal User user) {
        scrapService.deleteScrap(guideId, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/mypage/scraps/card")
    public ResponseEntity<List<GuideCardItemDto>> getMyScrapCards(@AuthenticationPrincipal User user) {
        List<GuideCardItemDto> responseDto = scrapService.getMyScrapCards(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/api/mypage/scraps")
    public ResponseEntity<List<GuideListItemDto>> getMyScrapList(@AuthenticationPrincipal User user) {
        List<GuideListItemDto> responseDto = scrapService.getMyScrapList(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
