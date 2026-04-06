package com.noman.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // CSRF disable (dev ke liye)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Sab endpoints open
            );
        return http.build();
    }
}