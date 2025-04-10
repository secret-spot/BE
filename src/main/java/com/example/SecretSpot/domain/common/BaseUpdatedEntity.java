package com.example.SecretSpot.domain.common;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseUpdatedEntity {
    @Column(nullable = false)
    protected LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
