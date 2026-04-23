package com.noman.ems.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.noman.ems.entity.User;
import com.noman.ems.repository.UserRepository;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationEntryPoint entryPoint;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ❌ disable CSRF (REST ke liye)
            .csrf(AbstractHttpConfigurer::disable)
            
            .exceptionHandling(ex -> ex
            	    .authenticationEntryPoint(entryPoint)  
            	    .accessDeniedHandler(accessDeniedHandler)
            	)

            // jwt -> STATELESS (NO SESSION)
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
                

            // ❌ custom exception handling
            .exceptionHandling(ex -> ex
                    .accessDeniedHandler(accessDeniedHandler)
            )

            

            // authorization rules
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
            );

            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    //  password encoder
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

    
    
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EMS API")
                        .version("1.0"))

                // 🔐 Add JWT security
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
    
}


