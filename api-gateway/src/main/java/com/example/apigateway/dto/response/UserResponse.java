package com.example.apigateway.dto.response;

import com.example.apigateway.enumn.UserRoleEnum;
import lombok.Data;

@Data
public class UserResponse {

    private String username;

    private String fullName;

    private String phoneNumber;

    private String email;

    private UserRoleEnum role;
}
