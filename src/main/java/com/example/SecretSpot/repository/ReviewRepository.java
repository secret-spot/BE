package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Review;
import com.example.SecretSpot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(long userId);

    Integer countByUserId(Long userId);

    List<Review> findAllByGuideId(Long guideId);

    boolean existsByGuideIdAndUserId(Long guideId, Long userId);

    List<Review> findAllByGuideIdOrderByCreatedAtDesc(Long guideId);

    Optional<Review> findByGuideIdAndUser(Long guideId, User user);

    List<Review> findAllByUserId(Long userId);
}
