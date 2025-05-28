package com.example.apigateway.filter;

import com.example.apigateway.constant.Constant;
import com.example.apigateway.enumn.UserRoleEnum;
import com.example.apigateway.security.CustomUserDetailService;
import com.example.apigateway.service.IPermissionService;
import com.example.apigateway.service.ITokenService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JwtAuthenticationFilter is a filter that checks for JWT tokens in the request headers.
 * If a valid token is found, it authenticates the user and sets the security context.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ITokenService tokenService;
    private final CustomUserDetailService userDetailsService;
    private List<RequestMatcher> permitAllMatchers;
    private final IPermissionService permissionService;

    @PostConstruct
    public void init() {
        // Khởi tạo danh sách các endpoint ANONYMOUS
        permitAllMatchers = new ArrayList<>();
        permissionService.getPermissionsByRoleName(Constant.ANONYMOUS).forEach(permission ->
                permitAllMatchers.add(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.valueOf(permission.getMethod()),permission.getEndpoint())));
    }
    /**
     * Filters incoming requests to check for JWT tokens and authenticate users.
     *
     * @param request  the incoming HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to continue processing
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = tokenService.extractUsername(token);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(tokenService.isTokenValid(token, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Determines whether the filter should not process the request.
     * If the request matches any of the permitAllMatchers, it will not be filtered.
     *
     * @param request the incoming HTTP request
     * @return true if the request should not be filtered, false otherwise
     * @throws ServletException if an error occurs during filtering
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return permitAllMatchers.stream()
                .anyMatch(matcher -> matcher.matches(request));
    }
}