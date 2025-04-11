package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.*;
import com.example.SecretSpot.service.GuideService;
import com.example.SecretSpot.web.dto.GuideDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GuideController {

    private final GuideService guideService;

    @PostMapping("/api/guides")
    public ResponseEntity<Long> guide(@RequestBody GuideDto guide, @AuthenticationPrincipal User user) {
        Long guideId = guideService.saveGuide(guide, user);
        return ResponseEntity.ok(guideId);
    }
}
