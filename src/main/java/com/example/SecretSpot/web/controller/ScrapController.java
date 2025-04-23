package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/api/guides/{guideId}/scraps")
    public ResponseEntity<Object> createScrap(@PathVariable Long guideId, @AuthenticationPrincipal User user) throws BadRequestException {
        scrapService.createScrap(guideId, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/api/guides/{guideId}/scraps")
    public ResponseEntity<Object> deleteScrap(@PathVariable Long guideId, @AuthenticationPrincipal User user) {
        scrapService.deleteScrap(guideId, user);
        return ResponseEntity.noContent().build();
    }
}
