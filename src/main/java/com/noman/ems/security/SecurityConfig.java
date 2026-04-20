package com.noman.ems.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.noman.ems.entity.User;
import com.noman.ems.repository.UserRepository;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthFailureHandler failureHandler;

    @Autowired
    private CustomAuthSuccessHandler successHandler;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ❌ disable CSRF (REST ke liye)
            .csrf(AbstractHttpConfigurer::disable)

            // ❌ disable basic auth
            .httpBasic(AbstractHttpConfigurer::disable)

            // 🔥 CUSTOM LOGIN ENABLE
            .formLogin(form -> form
                    .loginProcessingUrl("/login")   // 🔥 yahi endpoint hit hoga
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .permitAll()
            )

            // ❌ custom exception handling
            .exceptionHandling(ex -> ex
                    .accessDeniedHandler(accessDeniedHandler)
            )

            // 🔐 session management
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )

            // 🔐 authorization rules
            .authorizeHttpRequests(auth -> auth

                    // Swagger allow
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-ui.html"
                    ).permitAll()

                    // public APIs
                    .requestMatchers("/auth/**").permitAll()

                    // role-based access
                    .requestMatchers("/admin/**", "/projects/**").hasRole("ADMIN")
                    .requestMatchers("/employees/**").hasAnyRole("EMPLOYEE", "ADMIN")
                    .requestMatchers("/clients/**").hasAnyRole("CLIENT", "ADMIN")

                    // baki sab login required
                    .anyRequest().authenticated()
            )

            // 🔥 userDetailsService connect
            .userDetailsService(userDetailsService);

        return http.build();
    }

    // 🔐 password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 👇 auto admin create
    @Bean
    CommandLineRunner createAdmin(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {

            if (userRepo.findByEmail("admin@gmail.com").isEmpty()) {

                User admin = new User();
                admin.setEmail("admin@gmail.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                admin.setEnabled(true);
                admin.setFailedAttempts(0);
                admin.setAccountLocked(false);

                userRepo.save(admin);

                System.out.println("Admin created automatically");
            }
        };
    }

    // Swagger config
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EMS API")
                        .version("1.0"));
    }
}