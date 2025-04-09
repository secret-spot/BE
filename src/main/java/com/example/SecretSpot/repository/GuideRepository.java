package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Integer> {
    List<Guide> findTop5ByOrderByCreatedDateDesc();
}
