package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.GuideKeyword;
import com.example.SecretSpot.domain.Keyword;
import com.example.SecretSpot.domain.compositekeys.GuideKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideKeywordRepository extends JpaRepository<GuideKeyword, GuideKeywordId> {
    List<Guide> findByKeyword(Keyword keyword);
    boolean existsByGuideAndKeyword(Guide guide, Keyword keyword);
}
