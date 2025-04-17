package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Ranking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Optional<Ranking> findByUserId(Long userId);

    Optional<Ranking> findTop1ByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("""
            SELECT r
              FROM Ranking r
             WHERE r.createdAt = (
                     SELECT MAX(r2.createdAt) FROM Ranking r2
                   )
             ORDER BY r.ranking ASC
            """)
    List<Ranking> findLatestSnapshotRankings(Pageable page);
}