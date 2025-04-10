package com.example.SecretSpot.config.security;

import com.example.SecretSpot.repository.UserRepository;
import com.example.SecretSpot.web.dto.TokenDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        TokenDto tokens = jwtProvider.createTokens(email);
        String accessToken = tokens.getAccessToken();
        String refreshToken = tokens.getRefreshToken();

        // 첫 로그인 시 DB에 저장
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");
        String redirectUri;

//        if (userRepository.findByEmail(email)) { // 첫 로그인
        if (true) {
            redirectUri = UriComponentsBuilder
                    .fromUriString("http://localhost:4200/oauth2/redirect")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .queryParam("username", name)
                    .encode()
                    .build()
                    .toUriString();
//            userRepository.save(User.builder().name(name).email(email)
//                        .picture(picture).role(Role.ROLE_USER)
        }
        else {
            redirectUri = UriComponentsBuilder
                    .fromUriString("http://localhost:4200/oauth2/redirect")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build()
                    .toUriString();
        }

        response.sendRedirect(redirectUri);

        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
    }
}
