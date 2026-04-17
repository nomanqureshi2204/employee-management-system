package com.noman.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // ✅ Swagger allow
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html"
                ).permitAll()

                // ✅ Auth APIs allow
                .requestMatchers(
                        "/set-password",
                        "/login/**"
                ).permitAll()

                // ✅ TEMP: allow employee & client APIs (testing ke liye)
                .requestMatchers(
                        "/employees/**",
                        "/clients/**"
                ).permitAll()

                // 🔒 baaki sab secure
                .anyRequest().authenticated()
            )

            // ❌ Disable default login page
            .formLogin(form -> form.disable())

            // ❌ Disable basic auth popup
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    // 🔐 Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}