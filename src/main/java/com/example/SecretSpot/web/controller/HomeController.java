package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Region;
import com.example.SecretSpot.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    HomeService homeService;

    @GetMapping("/api/home")
    public Map<String, Object> home() {
        List<Guide> latestGuide = homeService.getLatestGuide();
        List<Guide> recommendedGuide = homeService.getRecommendedGuide();
        List<Region> recommendedRegion = homeService.getRecommendedRegion();

        Map<String, Object> model = new HashMap<>();
        model.put("latestGuide", latestGuide);
        model.put("recommendedGuide", recommendedGuide);
        model.put("recommendedRegion", recommendedRegion);

        return model;
    }
}
