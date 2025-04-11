package com.example.SecretSpot.service;

import com.example.SecretSpot.domain.Keyword;
import com.example.SecretSpot.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public List<Keyword> getKeywords(List<String> keywords) {
        List<Keyword> keywordList = new ArrayList<Keyword>();
        for (String stringKeyword : keywords) {
            Keyword keyword = keywordRepository.findByName(stringKeyword).orElse(null);
            if (keyword != null) {
                keywordList.add(keyword);
            }
        }
        return keywordList;
    }
}
