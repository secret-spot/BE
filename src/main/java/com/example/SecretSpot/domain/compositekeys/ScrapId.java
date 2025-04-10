package com.example.SecretSpot.domain.compositekeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ScrapId implements java.io.Serializable {
    private static final long serialVersionUID = -85068949976468609L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "guide_id", nullable = false)
    private Long guideId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ScrapId entity = (ScrapId) o;
        return Objects.equals(this.guideId, entity.guideId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guideId, userId);
    }

}