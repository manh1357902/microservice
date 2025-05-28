package com.example.apigateway.dto.response;

import lombok.Data;

import java.time.Instant;

@Data
public class RefreshToken {
    private String token;
    private Long userId;
    private Instant expiryDate;
}
