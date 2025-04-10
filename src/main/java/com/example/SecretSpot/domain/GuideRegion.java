package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.compositekeys.GuideRegionId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "guide_regions")
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

}