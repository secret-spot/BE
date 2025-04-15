package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    List<Guide> findTop3ByUserIdOrderByCreatedAtDesc(Long userId);

    List<Guide> findTop5ByOrderByCreatedAtDesc();
}
