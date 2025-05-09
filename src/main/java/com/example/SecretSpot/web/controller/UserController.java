package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.common.util.UserUtils;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.service.UserService;
import com.example.SecretSpot.web.dto.GuideCardItemDto;
import com.example.SecretSpot.web.dto.ProfileUpdateRequestDto;
import com.example.SecretSpot.web.dto.UserNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<Void> updateProfile(@RequestPart(value = "data", required = false) ProfileUpdateRequestDto updateRequestDto,
                                              @RequestPart(value = "image", required = false) MultipartFile newProfileImage,
                                              @AuthenticationPrincipal User user) throws IOException {
        userService.updateUserProfile(updateRequestDto, newProfileImage, user.getEmail());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/mypage/guides")
    public ResponseEntity<List<GuideCardItemDto>> getGuides(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserGuides(user));
    }

    @GetMapping("/api/users/username")
    public ResponseEntity<UserNameDto> getMyNicknameOrName(@AuthenticationPrincipal User user) {
        UserNameDto responseDto = UserNameDto.builder().userName(UserUtils.getNicknameOrName(user)).build();
        return ResponseEntity.ok(responseDto);
    }
}
