package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Keyword;
import com.example.SecretSpot.domain.RegionKeyword;
import com.example.SecretSpot.domain.Region;
import com.example.SecretSpot.domain.compositekeys.RegionKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionKeywordRepository extends JpaRepository<RegionKeyword, RegionKeywordId> {
    @Query("SELECT rk.region FROM RegionKeyword rk WHERE rk.keyword IN :keywords GROUP BY rk.region ORDER BY COUNT(rk.keyword) DESC")
    List<Region> findRegionsByUserKeywordsOrderByMatchCount(@Param("keywords") List<Keyword> keywords);

    List<RegionKeyword> findByRegion(Region region);
}
