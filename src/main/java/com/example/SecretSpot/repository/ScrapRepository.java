package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.Scrap;
import com.example.SecretSpot.domain.compositekeys.ScrapId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {

}
