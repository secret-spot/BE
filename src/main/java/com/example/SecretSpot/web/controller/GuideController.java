package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.service.GuideService;
import com.example.SecretSpot.service.KeywordService;
import com.example.SecretSpot.service.RegionService;
import com.example.SecretSpot.web.dto.DetailedGuideDto;
import com.example.SecretSpot.web.dto.GuideDto;
import com.example.SecretSpot.web.dto.GuideListItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuideController {

    private final GuideService guideService;
    private final KeywordService keywordService;
    private final RegionService regionService;

    @PostMapping("/api/guides")
    public ResponseEntity<Long> createGuide(@RequestPart("data") GuideDto guide,
                                            @RequestPart("images") List<MultipartFile> images,
                                            @AuthenticationPrincipal User user) throws IOException {
        Long guideId = guideService.saveGuide(guide, images, user);
        return ResponseEntity.ok(guideId);
    }

    @GetMapping("/api/guides/hidden")
    public ResponseEntity<List<GuideListItemDto>> getHiddenGuide() {
        List<GuideListItemDto> responseDto = guideService.getHiddenGuide();
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/api/guides/{id}/analyze")
    public ResponseEntity<Object> analyzeGuide(@PathVariable Long id) {
        guideService.analyzeGuide(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/guides/{id}")
    public ResponseEntity<DetailedGuideDto> getDetailedGuide(@PathVariable Long id, @AuthenticationPrincipal User user) {
        DetailedGuideDto detailedGuide = guideService.getDetailedGuide(id, user);
        return ResponseEntity.ok(detailedGuide);
    }
}
