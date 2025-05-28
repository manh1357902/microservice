package com.example.authservice.service.impl;

import com.example.authservice.dto.request.AuthRequest;
import com.example.authservice.dto.request.LoginRequest;
import com.example.authservice.dto.request.RefreshTokenRequest;
import com.example.authservice.dto.response.AuthResponse;
import com.example.authservice.dto.response.RefreshToken;
import com.example.authservice.entity.User;
import com.example.authservice.enumn.Permission;
import com.example.authservice.enumn.UserRoleEnum;
import com.example.authservice.exception.NotFoundException;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.CustomUserDetail;
import com.example.authservice.service.IAuthService;
import com.example.authservice.service.IRefreshTokenService;
import com.example.authservice.service.ITokenService;
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


    @Override
    public Boolean authentication(AuthRequest authRequest) {
        String requestPath = extractPath(authRequest.getUrlRequest());
        String method = authRequest.getMethodRequest();

        // 1. Check quyền public (Anonymous)
        if (hasPermission(UserRoleEnum.ANONYMOUS.getPermissions(), requestPath, method)) {
            return true;
        }

        // 2. Check quyền người dùng đăng nhập
        String username = tokenService.extractUsername(authRequest.getToken());
        if (username != null) {
            CustomUserDetail userDetails = (CustomUserDetail) userDetailsService.loadUserByUsername(username);
            if (tokenService.isTokenValid(authRequest.getToken(), userDetails)) {
                return hasPermission(userDetails.getUser().getRole().getPermissions(), requestPath, method);
            }
        }

        return false;
    }

    private boolean hasPermission(List<Permission> permissions, String requestPath, String method) {
        requestPath = normalizePath(requestPath);
        for (Permission p : permissions) {
            String permissionPath = normalizePath(p.getEndpoint());
            if (pathMatcher.match(permissionPath, requestPath)
                    && p.getMethod().name().equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }

    private String extractPath(String url) {
        if (url == null) return "";
        int idx = url.indexOf('?');
        return (idx >= 0) ? url.substring(0, idx) : url;
    }

    private String normalizePath(String path) {
        return (path == null) ? "" : path.replaceAll("/+$", "");
    }
}
