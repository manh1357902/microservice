package com.example.apigateway.security;


import com.example.apigateway.constant.Constant;
import com.example.apigateway.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        log.error("Unauthorized error: {}", authException.getMessage());
        ErrorResponse<Object> error = ErrorResponse.of(Constant.UNAUTHORIZED, "Access denied: missing or invalid authentication token.");
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}