package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(long userId);

    Integer countByUserId(Long userId);

    List<Review> findAllByGuideId(Long guideId);

    boolean existsByGuideIdAndUserId(Long guideId, Long userId);
}
