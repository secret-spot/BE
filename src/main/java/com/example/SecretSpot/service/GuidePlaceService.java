package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.GuidePlace;
import com.example.SecretSpot.repository.GuidePlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuidePlaceService {

    private final GuidePlaceRepository guidePlaceRepository;

    public Map<Long, List<String>> getGuidePlaceNames(List<Long> guideIds) {
        List<GuidePlace> guidePlaces = guidePlaceRepository.findAllById_guideIdIn(guideIds);

        return guidePlaces.stream()
                .collect(Collectors.groupingBy(
                        gp -> gp.getGuide().getId(),
                        Collectors.mapping(gp -> gp.getPlace().getName(), Collectors.toList())
                ));
    }
}
