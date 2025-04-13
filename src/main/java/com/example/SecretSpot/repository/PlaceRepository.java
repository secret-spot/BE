package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findByGoogleId(String googleId);
}
