package com.example.SecretSpot.domain.compositekeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class RegionKeywordId implements java.io.Serializable {
    private static final long serialVersionUID = -6580697281328762608L;
    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    public RegionKeywordId(Long keywordId, Long regionId) {
        this.keywordId = keywordId;
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionKeywordId entity = (RegionKeywordId) o;
        return Objects.equals(this.keywordId, entity.keywordId) &&
                Objects.equals(this.regionId, entity.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywordId, regionId);
    }

}