package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.GuideRegion;
import com.example.SecretSpot.domain.Region;
import com.example.SecretSpot.domain.compositekeys.GuideRegionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRegionRepository extends JpaRepository<GuideRegion, GuideRegionId> {
    boolean existsByGuideAndRegion(Guide guide, Region region);
}
