package com.example.authservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request with username and password")
public class LoginRequest {

    @Schema(description = "Username of the user", example = "nguyenvana")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "Password of the user", example = "123456")
    @NotBlank(message = "Password is required")
    private String password;
}
