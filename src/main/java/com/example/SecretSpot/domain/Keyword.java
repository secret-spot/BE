package com.example.SecretSpot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "keywords")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;
}