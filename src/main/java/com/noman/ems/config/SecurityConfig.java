package com.noman.ems.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // Public APIs
                .requestMatchers("/login/**", "/set-password").permitAll()

                // Admin
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Employee
                .requestMatchers("/employees/**").hasRole("EMPLOYEE")

                // Client
                .requestMatchers("/clients/**").hasRole("CLIENT")

                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}