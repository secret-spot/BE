package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.common.BaseTimeEntity;
import com.example.SecretSpot.domain.compositekeys.UserKeywordId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_keywords")
public class UserKeyword extends BaseTimeEntity {
    @EmbeddedId
    private UserKeywordId id;

    @MapsId("keywordId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}