package com.example.apigateway.configuration;


import com.example.apigateway.constant.Constant;
import com.example.apigateway.entity.Permission;
import com.example.apigateway.entity.Role;
import com.example.apigateway.security.CustomAccessDeniedHandler;
import com.example.apigateway.security.CustomAuthenticationEntryPoint;
import com.example.apigateway.filter.JwtAuthenticationFilter;
import com.example.apigateway.service.IPermissionService;
import com.example.apigateway.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Security configuration for the application using JWT authentication.
 * Configures endpoint access, disables CSRF for REST APIs, and registers the JWT filter.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final IPermissionService permissionService;
    private final IRoleService roleService;
    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<Permission> permissionsPublic = permissionService.getPermissionsByRoleName(Constant.ANONYMOUS);
        List<Permission> permissions = permissionService.getAllPermissions();
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                permissionsPublic.forEach(permission -> {
                    auth.requestMatchers(HttpMethod.valueOf(permission.getMethod()), permission.getEndpoint()).permitAll();
                });
                permissions.forEach(permission -> {
                    if(!permissionsPublic.contains(permission)){
                        String[] listRoleName = permission.getRoles().stream().map(Role::getName).toArray(String[]::new);
                        auth.requestMatchers(HttpMethod.valueOf(permission.getMethod()), permission.getEndpoint()).hasAnyAuthority(listRoleName);
                    }
                });
                auth.anyRequest().authenticated();
            })
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)

            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Configures the authentication manager for the application.
     *
     * @param authenticationConfiguration the AuthenticationConfiguration object
     * @return the configured AuthenticationManager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /**
     * Configures CORS settings for the application.
     *
     * @return the configured UrlBasedCorsConfigurationSource
     */
    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedHeader(Constant.ASTERISK);
        configuration.addAllowedMethod(Constant.ASTERISK);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(Constant.SLASH_ASTERISK_ASTERISK, configuration);
        return source;
    }

    /**
     * Configures the password encoder for the application.
     *
     * @return the configured PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
