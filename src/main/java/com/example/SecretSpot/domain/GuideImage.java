package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "guide_images",
        uniqueConstraints = @UniqueConstraint(columnNames = {"guide_id", "sortOrder"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuideImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @Lob
    @Column(nullable = false)
    private String url;

    @Column(name = "sort_order", nullable = false)
    @Builder.Default
    private Integer sortOrder = 1;
}
