package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.*;
import com.example.SecretSpot.service.GuideService;
import com.example.SecretSpot.service.KeywordService;
import com.example.SecretSpot.service.RegionService;
import com.example.SecretSpot.web.dto.GuideDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GuideController {

    private final GuideService guideService;
    private final KeywordService keywordService;
    private final RegionService regionService;

    @PostMapping("/api/guides")
    public ResponseEntity<Long> guide(@RequestBody GuideDto guide, @AuthenticationPrincipal User user) {
        Long guideId = guideService.saveGuide(guide, user);
        return ResponseEntity.ok(guideId);
    }

    @PostMapping("/api/guides/{id}/analyze")
    public ResponseEntity<Object> analyzeGuide(@PathVariable Long id) {
        guideService.analyzeGuide(id);
        return ResponseEntity.ok().build();
    }
}
