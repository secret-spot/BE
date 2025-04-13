package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.compositekeys.GuidePlaceId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "guide_places")
public class GuidePlace {
    @EmbeddedId
    private GuidePlaceId id;

    @MapsId("guideId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @MapsId("placeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Builder
    public GuidePlace(Guide guide, Place place) {
        this.guide = guide;
        this.place = place;
        this.id = new GuidePlaceId(guide.getId(), place.getId());
    }
}