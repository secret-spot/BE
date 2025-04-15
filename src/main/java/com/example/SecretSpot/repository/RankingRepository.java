package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Optional<Ranking> findByUserId(Long userId);
}
