package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.GuideRegion;
import com.example.SecretSpot.repository.GuideRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideRegionService {

    private final GuideRegionRepository guideRegionRepository;

    public Map<Long, List<String>> getGuideRegionNames(List<Long> guideIds) {
        List<GuideRegion> guideRegions = guideRegionRepository.findAllById_guideIdIn(guideIds);

        return guideRegions.stream()
                .collect(Collectors.groupingBy(
                        gr -> gr.getGuide().getId(),
                        Collectors.mapping(gr -> gr.getRegion().getName(), Collectors.toList())
                ));
    }
}
