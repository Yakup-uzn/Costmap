package com.yakos.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yakos.map.model.RefreshToken;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUsername(String username);

	void deleteAllByExpiryDateBefore(Instant now);
}