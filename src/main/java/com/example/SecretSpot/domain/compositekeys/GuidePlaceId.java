package com.example.SecretSpot.domain.compositekeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class GuidePlaceId implements java.io.Serializable {
    private static final long serialVersionUID = -8876018727605485240L;
    @Column(name = "guide_id", nullable = false)
    private Long guideId;

    @Column(name = "place_id", nullable = false)
    private Long placeId;

    public GuidePlaceId(Long guideId, Long placeId) {
        this.guideId = guideId;
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GuidePlaceId entity = (GuidePlaceId) o;
        return Objects.equals(this.guideId, entity.guideId) &&
                Objects.equals(this.placeId, entity.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guideId, placeId);
    }

}