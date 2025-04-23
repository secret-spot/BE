package com.example.SecretSpot.domain.compositekeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class GuideRegionId implements java.io.Serializable {
    private static final long serialVersionUID = -4013490206883211188L;
    @Column(name = "guide_id", nullable = false)
    private Long guideId;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    public GuideRegionId(Long guideId, Long regionId) {
        this.guideId = guideId;
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuideRegionId entity = (GuideRegionId) o;
        return Objects.equals(this.regionId, entity.regionId) &&
                Objects.equals(this.guideId, entity.guideId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionId, guideId);
    }

}