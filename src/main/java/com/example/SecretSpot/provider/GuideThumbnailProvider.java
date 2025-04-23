package com.example.SecretSpot.provider;

import com.example.SecretSpot.domain.GuideImage;
import com.example.SecretSpot.repository.GuideImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuideThumbnailProvider {
    private final GuideImageRepository guideImageRepository;
    
    /**
     * 썸네일 반환 함수
     */
    public String getThumbnailUrl(Long guideId) {
        GuideImage image = guideImageRepository.findTop1ByGuideIdOrderBySortOrderAsc(guideId);
        return image != null ? image.getUrl() : null;
    }
}
