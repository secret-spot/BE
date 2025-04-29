package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.compositekeys.RegionKeywordId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "region_keywords")
@NoArgsConstructor
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

    @Builder
    public RegionKeyword(Keyword keyword, Region region) {
        this.keyword = keyword;
        this.region = region;
        this.id = new RegionKeywordId(keyword.getId(), region.getId());
    }

}