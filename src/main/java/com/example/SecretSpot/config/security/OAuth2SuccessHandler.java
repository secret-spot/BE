package com.example.SecretSpot.config.security;

import com.example.SecretSpot.domain.Ranking;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.repository.RankingRepository;
import com.example.SecretSpot.repository.UserRepository;
import com.example.SecretSpot.web.dto.TokenDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RankingRepository rankingRepository;

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

        if (!userRepository.existsByEmail(email)) {
            redirectUri = UriComponentsBuilder
                    .fromUriString("http://localhost:4200/oauth2/redirect")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .queryParam("username", name)
                    .encode()
                    .build()
                    .toUriString();
            User user = userRepository.save(User.builder().name(name).email(email)
                        .profileImageUrl(picture).build());
            rankingRepository.save(Ranking.builder().ranking(userRepository.count()).user(user).build());
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
