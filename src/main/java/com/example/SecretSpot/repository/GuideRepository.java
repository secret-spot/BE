package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.Keyword;
import com.example.SecretSpot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    List<Guide> findTop3ByUserIdOrderByCreatedAtDesc(Long userId);

    List<Guide> findTop5ByOrderByCreatedAtDesc();

    List<Guide> findTop10ByOrderByRarityPointDesc();

    Integer countByUserId(Long userId);

    @Query("SELECT SUM(g.rarityPoint) FROM Guide g WHERE g.user.id = :userId")
    Integer sumRarityPointByUserId(@Param("userId") Long userId);

    Integer countByUserIdAndReviewRatingGreaterThanEqual(Long userId, double v);

    List<Guide> findAllByIdIn(List<Long> guideIds);

    List<Guide> findByUser(User user);

    @Query("""
            SELECT DISTINCT gk.guide
            FROM GuideKeyword gk
            WHERE gk.keyword IN :userKeywords
            """)
    List<Guide> findGuidesByUserKeywords(@Param("userKeywords") List<Keyword> userKeywords);

    @Query("""
            SELECT DISTINCT g FROM Guide g
            LEFT JOIN g.keywords k
            LEFT JOIN k.keyword kw
            WHERE g.title LIKE CONCAT('%', :keyword, '%')
            OR g.content LIKE CONCAT('%', :keyword, '%')
            OR kw.name LIKE CONCAT('%', :keyword, '%')
            """)
    List<Guide> searchByKeyword(@Param("keyword") String keyword);

}
