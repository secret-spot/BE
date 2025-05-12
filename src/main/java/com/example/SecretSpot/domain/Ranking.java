package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.common.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "rankings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Ranking extends BaseCreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "ranking", nullable = false)
    private Long ranking;

    @Column(name = "total_point", nullable = false, insertable = false, updatable = false)
    private Integer totalPoint;

    @Column(name = "guide_point", nullable = false)
    @Builder.Default
    private Integer guidePoints = 0;

    @Column(name = "rarity_point", nullable = false)
    @Builder.Default
    private Integer rarityPoint = 0;

    @Column(name = "review_point", nullable = false)
    @Builder.Default
    private Integer reviewPoint = 0;

    @Column(name = "guide_rating_point", nullable = false)
    @Builder.Default
    private Integer guideRatingPoint = 0;
}