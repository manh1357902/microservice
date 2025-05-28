package com.example.apigateway.service.impl;

import com.example.apigateway.dto.response.RefreshToken;
import com.example.apigateway.entity.User;
import com.example.apigateway.exception.UnAuthorizedException;
import com.example.apigateway.repository.UserRepository;
import com.example.apigateway.service.IRefreshTokenService;
import com.example.apigateway.service.ITokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final UserRepository userRepository;

    private final ITokenService tokenService;

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    private final ObjectMapper objectMapper;

    @Value("${JWT_REFRESH_EXPIRATION_MS}")
    private long jwtRefreshExpiration;
    /**
     * Creates a new refresh token for a user and stores it in Redis.
     *
     * @param username the username of the user
     * @return the created RefreshToken
     * @throws RuntimeException if the user is not found
     */
    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(user.getId());
        String refreshTokenString = tokenService.generateRefreshToken(username, user);
        refreshToken.setToken(refreshTokenString);
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpiration));

        // Store in Redis with TTL
        String redisKey = REFRESH_TOKEN_PREFIX + refreshToken.getToken();
        redisTemplate.opsForValue().set(redisKey, refreshToken, jwtRefreshExpiration, TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    /**
     * Finds a refresh token by its token value in Redis.
     *
     * @param token the token value to search for
     * @return an Optional containing the found RefreshToken, or empty if not found
     */
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        String redisKey = REFRESH_TOKEN_PREFIX + token;
        Object data = redisTemplate.opsForValue().get(redisKey);

        RefreshToken refreshToken = null;
        if (data instanceof LinkedHashMap) {
            refreshToken = objectMapper.convertValue(data, RefreshToken.class);
        } else if (data instanceof RefreshToken) {
            refreshToken = (RefreshToken) data;
        }

        return Optional.ofNullable(refreshToken);
    }

    /**
     * Verifies if a refresh token is still valid.
     *
     * @param token the refresh token to verify
     * @throws UnAuthorizedException if the token is expired or not found
     */
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            String redisKey = REFRESH_TOKEN_PREFIX + token.getToken();
            redisTemplate.delete(redisKey);
            throw new UnAuthorizedException("Refresh token was expired. Please make a new sign-in request");
        }
    }
}
