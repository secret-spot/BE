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
public class GuideKeywordId implements java.io.Serializable {
    private static final long serialVersionUID = -5342062892287343200L;
    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "guide_id", nullable = false)
    private Long guideId;

    public GuideKeywordId(Long keywordId, Long guideId) {
        this.keywordId = keywordId;
        this.guideId = guideId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GuideKeywordId entity = (GuideKeywordId) o;
        return Objects.equals(this.keywordId, entity.keywordId) &&
                Objects.equals(this.guideId, entity.guideId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywordId, guideId);
    }

}