package com.example.SecretSpot.domain.compositekeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class ScrapId implements java.io.Serializable {
    private static final long serialVersionUID = -85068949976468609L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "guide_id", nullable = false)
    private Long guideId;

    public ScrapId(Long userId, Long guideId) {
        this.userId = userId;
        this.guideId = guideId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScrapId entity = (ScrapId) o;
        return Objects.equals(userId, entity.userId) &&
                Objects.equals(guideId, entity.guideId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, guideId);
    }

}