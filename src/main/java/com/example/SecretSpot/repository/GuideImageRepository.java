package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.GuideImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideImageRepository extends JpaRepository<GuideImage, Long> {
    Boolean existsByGuideAndUrl(Guide guide, String Url);

    GuideImage findTop1ByGuideIdOrderBySortOrderAsc(Long guideId);

    List<GuideImage> findByGuide_Id(Long guideId);
}
