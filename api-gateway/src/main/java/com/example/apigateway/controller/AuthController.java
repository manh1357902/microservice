package com.example.apigateway.controller;

import com.example.apigateway.constant.Constant;
import com.example.apigateway.dto.request.AuthRequest;
import com.example.apigateway.dto.request.LoginRequest;
import com.example.apigateway.dto.request.RefreshTokenRequest;
import com.example.apigateway.dto.response.ApiResponse;
import com.example.apigateway.service.IAuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related requests.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for login and token refresh")
public class AuthController {

    private final IAuthService authService;

    /**
     * Authenticates a user and returns access and refresh tokens along with user information.
     *
     * @param loginRequest the authentication request containing username and password
     * @return an AuthResponse containing access token, refresh token, username, and email
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(ApiResponse.success(Constant.SUCCESS, authService.login(loginRequest)));
    }

    /**
     * Generates a new access token using a valid refresh token.
     *
     * @param refreshTokenRequest the request containing the refresh token
     * @return an AuthResponse containing a new access token, the refresh token, username, and email
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(ApiResponse.success(Constant.SUCCESS, authService.refreshToken(refreshTokenRequest)));
    }

}