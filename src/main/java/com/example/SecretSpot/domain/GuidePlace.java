package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.compositekeys.GuidePlaceId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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

}