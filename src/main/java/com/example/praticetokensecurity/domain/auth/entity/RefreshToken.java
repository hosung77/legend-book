package com.example.praticetokensecurity.domain.auth.entity;

import com.example.praticetokensecurity.common.entity.TimeStamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;


    public void update(String refreshToken, LocalDateTime localDateTime) {
        this.token = refreshToken;
        this.expiryDate = localDateTime;
    }

    public RefreshToken(Long userId, String token, LocalDateTime expiryDate) {
        this.userId = userId;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public static RefreshToken of(Long userId, String token, LocalDateTime expiryDate) {
        return new RefreshToken(userId, token, expiryDate);
    }

}
