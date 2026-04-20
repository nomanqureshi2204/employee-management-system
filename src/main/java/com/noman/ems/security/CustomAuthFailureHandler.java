package com.noman.ems.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.noman.ems.entity.User;
import com.noman.ems.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepo;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException {

        // 🔥 Spring username ko "username" parameter me bhejta hai
        String email = request.getParameter("username");

        User user = userRepo.findByEmail(email).orElse(null);

        if (user != null) {

            int attempts = user.getFailedAttempts() + 1;
            user.setFailedAttempts(attempts);

            // 🔒 lock after 5 attempts
            if (attempts >= 5) {
                user.setAccountLocked(true);
                user.setLockTime(LocalDateTime.now().plusMinutes(5));
            }

            userRepo.save(user);
        }

        // response
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        response.getWriter().write(
                "{ \"status\": 401, \"error\": \"Invalid credentials\" }"
        );
    }
}