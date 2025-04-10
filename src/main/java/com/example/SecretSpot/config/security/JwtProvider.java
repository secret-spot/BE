package com.example.SecretSpot.config.security;

import com.example.SecretSpot.web.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final long EXPIRE_ACCESS = 1000 * 60 * 60 * 24; // 1일
    private final long EXPIRE_REFRESH = 1000 * 60 * 60 * 24 * 7; // 1주
    private SecretKey secretKey;

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

     public TokenDto createTokens(String email) {
         Date now = new Date();

         String accessToken = Jwts.builder().setSubject(email).setIssuedAt(now)
                 .setExpiration(new Date(now.getTime() + EXPIRE_ACCESS)).signWith(secretKey, SignatureAlgorithm.HS256)
                 .compact(); // 문자열 형태의 JWT 토큰 생성
         String refreshToken = Jwts.builder().setSubject(email).setIssuedAt(now)
                 .setExpiration(new Date(now.getTime() + EXPIRE_REFRESH)).signWith(secretKey, SignatureAlgorithm.HS256)
                 .compact();

         return new TokenDto(accessToken, refreshToken);
     }

    public String getEmailFromToken(String token) {
        // Subject로 설정해놓은 Email 가져오기
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
         try {
             Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
             return true;
         } catch (Exception e) {
             return false;
         }
    }
}
