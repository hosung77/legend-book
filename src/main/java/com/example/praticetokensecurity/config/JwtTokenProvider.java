package com.example.praticetokensecurity.config;

import com.example.praticetokensecurity.common.exception.CustomException;
import com.example.praticetokensecurity.common.exception.ErrorCode;
import com.example.praticetokensecurity.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

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


    public String createAccessToken(User user){
        Date now = new Date();
        long accessTokenExpireTime = 60 * 60 * 1000L; // 1시간

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(user.getEmail())
                        .claim("userId", user.getId())
                        .claim("userRole", user.getUserRole())
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + accessTokenExpireTime))
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken(User user){
        Date now = new Date();
        long refreshTokenExpireTime = 7 * 24 * 60 * 60 * 1000L; // 1주

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(user.getEmail())
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshTokenExpireTime))
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String subStringToken(String tokenValue){
        if(StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)){
            return tokenValue.substring(7);
        }
        throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)//  token이 이 key로 서명되었는지 확인함
                .getBody(); // 서명이 유효하면 claims(body)를 꺼냄
    }

}
