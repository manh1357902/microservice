package com.example.authservice.security;


import com.example.authservice.constant.Constant;
import com.example.authservice.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ErrorResponse<Object> error = ErrorResponse.of(
                Constant.FORBIDDEN,
                "Access denied: you do not have permission to access this resource."
        );
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}