package com.checkout.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/auth/**","/actuator/**").permitAll())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/products/**", "/api/v1/coupons/**")
                        .hasRole("ADMIN"))
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/carts/**").hasAnyRole("ADMIN", "USER"))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}