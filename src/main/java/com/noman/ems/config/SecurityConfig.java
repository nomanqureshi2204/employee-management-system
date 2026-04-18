package com.noman.ems.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import com.noman.ems.security.CustomUserDetailsService;

import io.swagger.v3.oas.models.OpenAPI;

import com.noman.ems.entity.User;
import com.noman.ems.repository.UserRepository;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // ❌ disable form login 
            .formLogin(form -> form.disable())

            // ❌ enablble basic auth
            .httpBasic(Customizer.withDefaults())

            .authorizeHttpRequests(auth -> auth

                // ✅ Swagger allow
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html"
                ).permitAll()

                // ✅ AUTH APIs allow (IMPORTANT FIX)
                .requestMatchers(
                        "/auth/**"   
                ).permitAll()

                // ADMIN ONLY
                .requestMatchers(
                        "/admin/**"
                ).hasRole("ADMIN")
                //EMPLOYEE ONLY
                .requestMatchers("/employees/**").hasRole("EMPLOYEE")

                // CLIENT ONLY
                .requestMatchers("/clients/**").hasRole("CLIENT")
                
                //REST all api
                .anyRequest().authenticated()
            ).userDetailsService(userDetailsService);

        return http.build();
    }

    // 🔐 Password Encoder
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
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("EMS API").version("1.0"))

                // 🔐 Security add
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"))

                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("basicAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                        )
                );
    }
}