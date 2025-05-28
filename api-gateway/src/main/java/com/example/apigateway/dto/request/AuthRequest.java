package com.example.apigateway.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "Token is required")
    private String token;
    @NotBlank(message = "URL request is required")
    private String urlRequest;
    @NotBlank(message = "Method request is required")
    private String methodRequest;
}
