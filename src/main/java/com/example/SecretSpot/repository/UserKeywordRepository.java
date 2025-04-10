package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.UserKeyword;
import com.example.SecretSpot.domain.compositekeys.UserKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, UserKeywordId> {

}
