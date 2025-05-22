package com.example.praticetokensecurity.config;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secretKey; // 이 키 값으로 우리가 발급한 토큰인지 확인할 수 있다.
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }


    public String createAccessToken(User user) {
        LocalDateTime expireTime = LocalDateTime.now().plusHours(1);
        LocalDateTime nowTime = LocalDateTime.now();

        Date expire = Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant());
        Date now = Date.from(nowTime.atZone(ZoneId.systemDefault()).toInstant());

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("userRole", user.getUserRole())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String createRefreshToken(User user) {
        LocalDateTime expireTime = LocalDateTime.now().plusDays(7);
        LocalDateTime nowTime = LocalDateTime.now();

        Date expire = Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant());
        Date now = Date.from(nowTime.atZone(ZoneId.systemDefault()).toInstant());

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String subStringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new ApiException(ErrorStatus.TOKEN_NOT_FOUND);
    }

    public Claims extractClaims(String token) {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)//  token이 이 key로 서명되었는지 확인함
                .getBody(); // 서명이 유효하면 claims(body)를 꺼냄
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException ex) {
            log.warn("잘못된 JWT 서명입니다: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.warn("만료된 JWT 토큰입니다: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.warn("지원되지 않는 JWT 토큰입니다: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.warn("JWT 토큰이 비었습니다: {}", ex.getMessage());
        }
        return false;
    }


    public String getEmail(String token) {

        String subject = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();

        System.out.println("✅ 파싱된 subject(email): " + subject);
        return subject;
    }

}
