package com.example.authservice.service;

import com.example.authservice.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

/**
 * Service interface for JWT token operations.
 */
public interface ITokenService {

    /**
     * Extracts the username from the JWT token.
     * @param token the JWT token
     * @return the username
     */
    String extractUsername(String token);

    /**
     * Extracts a specific claim from the JWT token.
     * @param token the JWT token
     * @param claimsResolver function to extract the claim
     * @return the claim value
     * @param <T> the type of the claim
     */
    <T> T extractClaim(String token, Function<io.jsonwebtoken.Claims, T> claimsResolver);

    /**
     * Generates an access token for the given user details.
     * @param username the username of the user
     * @return the access token
     */
    String generateAccessToken(String username, User user);

    /**
     * Generates a refresh token for the given user details.
     * @param username the username of the user
     * @return the refresh token
     */
    String generateRefreshToken(String username, User user);

    /**
     * Validates the JWT token for the given user details.
     * @param token the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
