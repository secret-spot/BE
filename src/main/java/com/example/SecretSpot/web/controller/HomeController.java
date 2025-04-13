package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Region;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/api/home")
    public ResponseEntity<Map<String, Object>> home(@AuthenticationPrincipal User user) {
        List<Guide> latestGuide = homeService.getLatestGuide();
        List<Guide> recommendedGuide = homeService.getRecommendedGuide(user);
        //List<Region> recommendedRegion = homeService.getRecommendedRegion();

        Map<String, Object> model = new HashMap<>();
        model.put("latestGuide", latestGuide);
        model.put("recommendedGuide", recommendedGuide);
        //model.put("recommendedRegion", recommendedRegion);

        return ResponseEntity.ok(model);
    }
}
