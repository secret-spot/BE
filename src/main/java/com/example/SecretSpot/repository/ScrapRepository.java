package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Scrap;
import com.example.SecretSpot.domain.compositekeys.ScrapId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {
    List<Scrap> findTop5ByUserIdOrderByCreatedAtDesc(Long id);

    List<Scrap> findAllByUserIdOrderByCreatedAtDesc(Long id);

}
