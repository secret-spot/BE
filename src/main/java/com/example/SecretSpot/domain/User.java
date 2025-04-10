package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.common.BaseTimeEntity;
import com.example.SecretSpot.domain.enums.RegisterType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2, nullable = false)
    @Builder.Default
    private String language = "ko";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private RegisterType registerType = RegisterType.GOOGLE;

    @Column(unique = true)
    private Long googleId;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 50)
    private String name;

    @Column(length = 30, unique = true)
    private String nickname;

    @Lob
    private String profileImageUrl;
}