package com.example.SecretSpot.config.security;

import com.example.SecretSpot.domain.Ranking;
import com.example.SecretSpot.domain.User;
import com.example.SecretSpot.domain.UserKeyword;
import com.example.SecretSpot.repository.KeywordRepository;
import com.example.SecretSpot.repository.RankingRepository;
import com.example.SecretSpot.repository.UserKeywordRepository;
import com.example.SecretSpot.repository.UserRepository;
import com.example.SecretSpot.web.dto.TokenDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final UserKeywordRepository userKeywordRepository;
    private final KeywordRepository keywordRepository;

    @Value("${REDIRECT_BASE_URL}")
    private String redirectBaseUrl;

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
                    .fromUriString(redirectBaseUrl + "/oauth2/redirect")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .queryParam("username", name)
                    .encode()
                    .build()
                    .toUriString();
            User user = userRepository.save(User.builder().name(name).email(email)
                    .profileImageUrl(picture).build());
            userRepository.flush();

            // 디폴트 키워드 저장
            userKeywordRepository.save(new UserKeyword(keywordRepository.findByName("친구").orElseThrow(), user));
            userKeywordRepository.save(new UserKeyword(keywordRepository.findByName("음식").orElseThrow(), user));
            userKeywordRepository.save(new UserKeyword(keywordRepository.findByName("힐링").orElseThrow(), user));

            rankingRepository.save(Ranking.builder().ranking(userRepository.count()).user(user).build());
        } else {
            redirectUri = UriComponentsBuilder
                    .fromUriString(redirectBaseUrl + "/oauth2/redirect")
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build()
                    .toUriString();
        }

        response.sendRedirect(redirectUri);
    }
}
