package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.UserKeyword;
import com.example.SecretSpot.repository.UserKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserKeywordService {

    private final UserKeywordRepository userKeywordRepository;

    public Map<Long, List<String>> getUserKeywordNames(List<Long> userIds) {
        List<UserKeyword> userKeywords = userKeywordRepository.findAllById_UserIdIn(userIds);

        return userKeywords.stream()
                .collect(Collectors.groupingBy(
                        uk -> uk.getUser().getId(),
                        Collectors.mapping(uk -> uk.getKeyword().getName(), Collectors.toList())
                ));
    }
}
