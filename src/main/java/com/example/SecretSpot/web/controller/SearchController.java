package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.service.SearchService;
import com.example.SecretSpot.web.dto.SearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/search/guides")
    public ResponseEntity<SearchResponseDto> searchGuides(@RequestParam String query) {
        SearchResponseDto responseDto = searchService.searchGuides(query);
        return ResponseEntity.ok(responseDto);
    }
}
