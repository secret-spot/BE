package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "qnas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Qna extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @Lob
    @Column(nullable = false)
    private String question;

    @Lob
    private String answer;
}
