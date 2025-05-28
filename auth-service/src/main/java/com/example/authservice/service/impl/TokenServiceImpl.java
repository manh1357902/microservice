package com.example.authservice.service.impl;

import com.example.authservice.entity.User;
import com.example.authservice.service.ITokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation of TokenService for JWT token operations.
 * Provides methods for generating, validating, and extracting information from JWT tokens.
 */
@Service
public class TokenServiceImpl implements ITokenService {

    @Value("${JWT_SECRET}")
    private String secretKey;

    @Value("${JWT_ACCESS_EXPIRATION_MS}")
    private long jwtAccessExpiration;

    @Value("${JWT_REFRESH_EXPIRATION_MS}")
    private long jwtRefreshExpiration;

    /**
     * Extracts the username from the JWT token.
     * @param token the JWT token
     * @return the username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     * @param token the JWT token
     * @param claimsResolver function to extract the claim
     * @return the claim value
     * @param <T> the type of the claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates an access token for the given user details.
     * @param username the username of the user
     * @return the access token
     */
    public String generateAccessToken(String username, User user) {
        return generateToken(new HashMap<>(), username, jwtAccessExpiration, user);
    }

    /**
     * Generates a refresh token for the given user details.
     * @param username the of the user
     * @return the refresh token
     */
    public String generateRefreshToken(String username, User user) {
        return generateToken(new HashMap<>(), username, jwtRefreshExpiration, user);
    }

    /**
     * Generates a JWT token with the given claims, user details, and expiration.
     * @param extraClaims additional claims to include in the token
     * @param username the of the user
     * @param expiration the expiration time in milliseconds
     * @return the generated JWT token
     */
    private String generateToken(Map<String, Object> extraClaims, String username, long expiration, User user) {
        extraClaims.put("userId", user.getId());
        extraClaims.put("role", user.getRole().name());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the JWT token for the given user details.
     * @param token the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if the JWT token is expired.
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     * @param token the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     * @param token the JWT token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Returns the signing key for JWT operations.
     * @return the signing key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
