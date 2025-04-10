package com.example.SecretSpot.repository;

import com.example.SecretSpot.domain.ChatbotHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotHistoryRepository extends JpaRepository<ChatbotHistory, Long> {

}
