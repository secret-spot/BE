package com.example.SecretSpot.domain;

import com.example.SecretSpot.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long googleId;

    @Column(length = 50, nullable = false)
    private String name;

    @Lob
    private String address;

    @Column(nullable = false)
    private Byte rating;
}