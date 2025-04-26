package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Guide;
import com.example.SecretSpot.domain.GuideKeyword;
import com.example.SecretSpot.domain.Keyword;
import com.example.SecretSpot.domain.compositekeys.GuideKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideKeywordRepository extends JpaRepository<GuideKeyword, GuideKeywordId> {
    List<GuideKeyword> findByKeyword(Keyword keyword);

    List<GuideKeyword> findAllById_guideIdIn(List<Long> guideIds);

    boolean existsByGuideAndKeyword(Guide guide, Keyword keyword);

    List<Long> guide(Guide guide);

    List<GuideKeyword> findByGuide_Id(Long guideId);
}
