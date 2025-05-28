package com.example.apigateway.service.impl;

import com.example.apigateway.dto.request.AuthRequest;
import com.example.apigateway.dto.request.LoginRequest;
import com.example.apigateway.dto.request.RefreshTokenRequest;
import com.example.apigateway.dto.response.AuthResponse;
import com.example.apigateway.dto.response.RefreshToken;
import com.example.apigateway.entity.User;
import com.example.apigateway.enumn.Permission;
import com.example.apigateway.enumn.UserRoleEnum;
import com.example.apigateway.exception.NotFoundException;
import com.example.apigateway.mapper.UserMapper;
import com.example.apigateway.repository.UserRepository;
import com.example.apigateway.security.CustomUserDetail;
import com.example.apigateway.service.IAuthService;
import com.example.apigateway.service.IRefreshTokenService;
import com.example.apigateway.service.ITokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * Implementation of AuthService for handling authentication operations.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;

    private final ITokenService tokenService;

    private final IRefreshTokenService refreshTokenService;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * Authenticates a user and returns access and refresh tokens along with user information.
     *
     * @param loginRequest the authentication request containing username and password
     * @return an AuthResponse containing access token, refresh token, username, and email
     */
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = ((CustomUserDetail) authentication.getPrincipal()).getUser();
        String accessToken = tokenService.generateAccessToken(user.getUsername(), user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken.getToken());
        response.setUser(userMapper.mapEntityToResponse(user));
        return response;
    }

    /**
     * Generates a new access token using a valid refresh token.
     *
     * @param refreshTokenRequest the request containing the refresh token
     * @return an AuthResponse containing a new access token, the refresh token, username, and email
     * @throws RuntimeException if the refresh token is invalid or expired
     */
    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .orElseThrow(() -> new NotFoundException("Refresh token not found"));
        refreshTokenService.verifyExpiration(refreshToken);

        User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        String accessToken = tokenService.generateAccessToken(user.getUsername(), user);

        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken.getToken());
        response.setUser(userMapper.mapEntityToResponse(user));
        return response;
    }
}
