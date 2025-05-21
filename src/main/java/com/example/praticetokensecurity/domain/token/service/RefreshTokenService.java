package com.example.praticetokensecurity.domain.token.service;

import com.example.praticetokensecurity.common.code.ErrorStatus;
import com.example.praticetokensecurity.common.error.ApiException;
import com.example.praticetokensecurity.config.JwtTokenProvider;
import com.example.praticetokensecurity.domain.token.entity.RefreshToken;
import com.example.praticetokensecurity.domain.token.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public RefreshToken saveToken(String refreshToken, Long userId) {
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(userId);

        if (existingToken.isPresent()) {
            RefreshToken token = existingToken.get();
            token.update(refreshToken, LocalDateTime.now().plusDays(7));
            refreshTokenRepository.save(token);
            return token;
        }

        RefreshToken newToken = RefreshToken.of(userId, refreshToken,
            LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(newToken);
        return newToken;
    }

    @Transactional
    public void updateRefreshToken(Long userId, String refreshToken, LocalDateTime expiry) {
        RefreshToken token = refreshTokenRepository.findByUserId(userId)
            .orElseThrow(() -> new ApiException(
                ErrorStatus.TOKEN_NOT_FOUND));

        token.update(refreshToken, expiry);
        refreshTokenRepository.save(token);
    }


    public void deleteRefreshToken(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }


}
