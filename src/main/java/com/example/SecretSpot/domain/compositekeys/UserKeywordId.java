package com.example.SecretSpot.domain.compositekeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class UserKeywordId implements java.io.Serializable {
    private static final long serialVersionUID = 5778116589726330709L;
    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public UserKeywordId(Long keywordId, Long userId) {
        this.keywordId = keywordId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserKeywordId entity = (UserKeywordId) o;
        return Objects.equals(this.keywordId, entity.keywordId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywordId, userId);
    }

}