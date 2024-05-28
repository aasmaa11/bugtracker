package com.bugtracker.SpringBootRestApp.security.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bugtracker.SpringBootRestApp.dao.RefreshTokenRepository;
import com.bugtracker.SpringBootRestApp.dao.UserAccountRepository;
import com.bugtracker.SpringBootRestApp.exception.TokenRefreshException;
import com.bugtracker.SpringBootRestApp.model.RefreshToken;

// Code from: https://www.bezkoder.com/spring-boot-refresh-token-jwt/
@Service
public class RefreshTokenService {

	@Value("${bugtracker.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserAccountRepository userRepository;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(Long userId) {
		
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUserAccount(userRepository.findById(userId));
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	@Transactional
	public RefreshToken deleteByUserId(Long userId) {
		RefreshToken refreshToken = refreshTokenRepository.findByUserAccountId(userId);
		refreshTokenRepository.delete(refreshToken);
		return refreshToken;
	}
}
