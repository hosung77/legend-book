package com.example.praticetokensecurity.domain.token.repository;

import com.example.praticetokensecurity.domain.token.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long userId);

    boolean existsByToken(String refreshToken);

    void deleteByUserId(Long userId);
}
