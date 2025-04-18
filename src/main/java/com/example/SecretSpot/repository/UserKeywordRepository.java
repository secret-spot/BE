package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.UserKeyword;
import com.example.SecretSpot.domain.compositekeys.UserKeywordId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, UserKeywordId> {
    List<UserKeyword> findByUserId(Long userId);

    List<UserKeyword> findAllById_UserIdIn(List<Long> userIds);


    @Transactional
    void deleteByUserId(Long userId);
}
