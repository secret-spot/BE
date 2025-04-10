package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.common.BaseTimeEntity;
import com.example.SecretSpot.domain.compositekeys.ScrapId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "scraps")
public class Scrap extends BaseTimeEntity {
    @EmbeddedId
    private ScrapId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("guideId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

}