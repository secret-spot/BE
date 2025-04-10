package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.compositekeys.RegionKeywordId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "region_keywords")
public class RegionKeyword {
    @EmbeddedId
    private RegionKeywordId id;

    @MapsId("keywordId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    @MapsId("regionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

}