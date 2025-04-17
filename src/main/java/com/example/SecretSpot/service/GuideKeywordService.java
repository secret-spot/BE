package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.GuideKeyword;
import com.example.SecretSpot.repository.GuideKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideKeywordService {

    private final GuideKeywordRepository guideKeywordRepository;

    public Map<Long, List<String>> getGuideKeywordNames(List<Long> guideIds) {
        List<GuideKeyword> guideKeywords = guideKeywordRepository.findAllById_guideIdIn(guideIds);

        return guideKeywords.stream()
                .collect(Collectors.groupingBy(
                        gk -> gk.getGuide().getId(),
                        Collectors.mapping(gk -> gk.getKeyword().getName(), Collectors.toList())
                ));
    }
}
