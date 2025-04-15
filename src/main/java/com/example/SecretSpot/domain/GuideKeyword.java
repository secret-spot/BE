package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.common.BaseUpdatedEntity;
import com.example.SecretSpot.domain.compositekeys.GuideKeywordId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "guide_keywords")
public class GuideKeyword extends BaseUpdatedEntity {
    @EmbeddedId
    private GuideKeywordId id;

    @MapsId("keywordId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    @MapsId("guideId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @Builder
    public GuideKeyword(Keyword keyword, Guide guide) {
        this.keyword = keyword;
        this.guide = guide;
        this.id = new GuideKeywordId(keyword.getId(), guide.getId());
    }
}