package com.example.apigateway.service;


import com.example.apigateway.dto.response.RefreshToken;

import java.util.Optional;

/**
 * Service interface for managing refresh tokens.
 */
public interface IRefreshTokenService {

    /**
     * Creates a new refresh token for a user and stores it in Redis.
     *
     * @param username the username of the user
     * @return the created RefreshToken
     * @throws RuntimeException if the user is not found
     */
    RefreshToken createRefreshToken(String username);

    /**
     * Finds a refresh token by its token value in Redis.
     *
     * @param token the token value to search for
     * @return an Optional containing the found RefreshToken, or empty if not found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Verifies if a refresh token is still valid.
     *
     * @param token the refresh token to verify
     * @throws RuntimeException if the token is expired or not found
     */
    void verifyExpiration(RefreshToken token);

}