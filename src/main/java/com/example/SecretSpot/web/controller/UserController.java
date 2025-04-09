package com.example.SecretSpot.web.controller;

import com.example.SecretSpot.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.naming.AuthenticationNotSupportedException;
import java.util.Map;

@Controller
public class UserController {

    @GetMapping("/api/mypage/profile")
    public String profile(@AuthenticationPrincipal User user) {
        return null;
    }
}
