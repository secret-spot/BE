package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
