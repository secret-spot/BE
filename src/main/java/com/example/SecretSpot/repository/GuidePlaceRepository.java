package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.GuidePlace;
import com.example.SecretSpot.domain.compositekeys.GuidePlaceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuidePlaceRepository extends JpaRepository<GuidePlace, GuidePlaceId> {
    List<GuidePlace> findAllById_guideIdIn(List<Long> guideIds);
}
