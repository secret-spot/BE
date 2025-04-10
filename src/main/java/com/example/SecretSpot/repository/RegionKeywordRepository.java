package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.RegionKeyword;
import com.example.SecretSpot.domain.compositekeys.RegionKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionKeywordRepository extends JpaRepository<RegionKeyword, RegionKeywordId> {

}
