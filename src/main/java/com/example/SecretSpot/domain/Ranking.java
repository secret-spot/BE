package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.common.BaseCreatedEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rankings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ranking extends BaseCreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "ranking", nullable = false)
    private Long ranking;

    @Column(name = "total_point", nullable = false)
    @Builder.Default
    private Integer totalPoint = 0;

    @Column(name = "guide_points", nullable = false)
    @Builder.Default
    private Integer guidePoints = 0;

    @Column(name = "hidden_points", nullable = false)
    @Builder.Default
    private Integer hiddenPoints = 0;

    @Column(name = "review_points", nullable = false)
    @Builder.Default
    private Integer reviewPoints = 0;
}