package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.mapper.GuideMapper;
import com.example.SecretSpot.service.UserService;
import com.example.SecretSpot.web.dto.GuideCardItemDto;
import com.example.SecretSpot.web.dto.ProfileUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/mypage/profile")
    public ResponseEntity<Map<String, Object>> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserProfile(user));
    }

    @PatchMapping("/api/mypage/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateRequestDto updateRequestDto, @AuthenticationPrincipal User user) {
        userService.updateUserProfile(updateRequestDto, user.getEmail());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/mypage/guides")
    public ResponseEntity<List<GuideCardItemDto>> getGuides(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserGuides(user));
    }
}
