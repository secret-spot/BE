package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    List<Guide> findTop3ByUserIdOrderByCreatedAtDesc(Long userId);

    List<Guide> findTop5ByOrderByCreatedAtDesc();

    Integer countByUserId(Long userId);

    @Query("SELECT SUM(g.rarityPoint) FROM Guide g WHERE g.user.id = :userId")
    Integer sumRarityPointByUserId(@Param("userId") Long userId);

    Integer countByUserIdAndReviewRatingGreaterThanEqual(Long userId, double v);
}
