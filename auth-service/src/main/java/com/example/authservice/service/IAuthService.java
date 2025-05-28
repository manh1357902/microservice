package com.example.authservice.service;


import com.example.authservice.dto.request.AuthRequest;
import com.example.authservice.dto.request.LoginRequest;
import com.example.authservice.dto.request.RefreshTokenRequest;
import com.example.authservice.dto.response.ApiResponse;
import com.example.authservice.dto.response.AuthResponse;

/**
 * Service interface for handling authentication-related operations.
 */
public interface IAuthService {

    /**
     * Authenticates a user and returns access and refresh tokens along with user information.
     *
     * @param loginRequest the authentication request containing username and password
     * @return an AuthResponse containing access token, refresh token, username, and email
     * @throws RuntimeException if authentication fails or user is not found
     */
    AuthResponse login(LoginRequest loginRequest);

    /**
     * Generates a new access token using a valid refresh token.
     *
     * @param refreshTokenRequest the request containing the refresh token
     * @return an AuthResponse containing a new access token, the refresh token, username, and email
     * @throws RuntimeException if the refresh token is invalid or expired
     */
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    Boolean authentication(AuthRequest authRequest);
}