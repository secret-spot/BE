package com.example.SecretSpot.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language {
    @Id
    @Column(length = 2)
    private String code;

    @Column(length = 30, nullable = false)
    private String name;
}

