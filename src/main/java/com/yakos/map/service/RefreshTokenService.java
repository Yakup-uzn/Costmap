package com.yakos.map.service;


import org.springframework.stereotype.Service;

import com.yakos.map.model.RefreshToken;
import com.yakos.map.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String username, String ipAddress, String userAgent) {
    	
    	refreshTokenRepository.deleteByUsername(username);
    	
        RefreshToken token = new RefreshToken();
        token.setUsername(username);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusSeconds(604800)); // 7 gün
        token.setIpAddress(ipAddress);
        token.setUserAgent(userAgent);
        return refreshTokenRepository.save(token);
    }

    public RefreshToken verifyRefreshToken(String token, String ipAddress, String userAgent) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // Kullanım sayısı kontrolü
        if (refreshToken.getUsageCount() >= 5) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token usage limit exceeded");
        }

        // Kullanım sayısını artır
        refreshToken.setUsageCount(refreshToken.getUsageCount() + 1);
        refreshTokenRepository.save(refreshToken);

        // Süresi ve cihaz bilgisi kontrolü yapılır
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        if (!refreshToken.getIpAddress().equals(ipAddress) || 
            !refreshToken.getUserAgent().equals(userAgent)) {
            throw new RuntimeException("Token does not match IP or device");
        }

        
        return refreshToken;
    }

    


    
    
    public void deleteByUsername(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }
}
