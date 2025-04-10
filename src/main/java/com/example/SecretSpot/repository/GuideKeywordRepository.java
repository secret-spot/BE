package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.GuideKeyword;
import com.example.SecretSpot.domain.compositekeys.GuideKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideKeywordRepository extends JpaRepository<GuideKeyword, GuideKeywordId> {

}
