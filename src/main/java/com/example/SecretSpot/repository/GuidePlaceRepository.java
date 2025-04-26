package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.GuidePlace;
import com.example.SecretSpot.domain.compositekeys.GuidePlaceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuidePlaceRepository extends JpaRepository<GuidePlace, GuidePlaceId> {
    List<GuidePlace> findAllById_guideIdIn(List<Long> guideIds);
    List<GuidePlace> findByGuide_Id(Long guideId);
}
