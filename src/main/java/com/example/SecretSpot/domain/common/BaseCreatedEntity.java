package com.example.SecretSpot.domain.common;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseCreatedEntity {
    @Column(updatable = false, nullable = false)
    protected LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}