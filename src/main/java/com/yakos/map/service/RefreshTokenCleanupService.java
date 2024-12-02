package com.yakos.map.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yakos.map.repository.RefreshTokenRepository;

import java.time.Instant;

@Service
public class RefreshTokenCleanupService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenCleanupService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // Süresi dolmuş Refresh Token'ları her gece 2'de temizle
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredTokens() {
        refreshTokenRepository.deleteAllByExpiryDateBefore(Instant.now());
        System.out.println("Expired tokens cleaned up at: " + Instant.now());
    }
}

