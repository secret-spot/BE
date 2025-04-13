package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.compositekeys.GuideRegionId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "guide_regions")
@NoArgsConstructor
public class GuideRegion {
    @EmbeddedId
    private GuideRegionId id;

    @MapsId("guideId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @MapsId("regionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Builder
    public GuideRegion(Guide guide, Region region) {
        this.guide = guide;
        this.region = region;
        this.id = new GuideRegionId(guide.getId(), region.getId());
    }
}