package com.noman.ems.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noman.ems.entity.Token;
import com.noman.ems.entity.User;
import com.noman.ems.repository.TokenRepository;
import com.noman.ems.repository.UserRepository;
import com.noman.ems.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // ===============================
    // 🔐 LOGIN WITH JWT + 5 ATTEMPTS
    // ===============================
    public Map<String, Object> login(String email, String password) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔒 Check account lock
        if (user.isAccountLocked()) {
            if (user.getLockTime().isAfter(LocalDateTime.now())) {
                return Map.of(
                        "status", 403,
                        "error", "Account locked! Try after some time"
                );
            } else {
                // unlock
                user.setAccountLocked(false);
                user.setFailedAttempts(0);
                userRepo.save(user);
            }
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String role = userDetails.getAuthorities()
                                     .iterator()
                                     .next()
                                     .getAuthority();

            String token = jwtUtil.generateToken(userDetails.getUsername(), role);

            // ✅ reset attempts on success
            user.setFailedAttempts(0);
            user.setAccountLocked(false);
            userRepo.save(user);

            return Map.of(
                    "status", 200,
                    "message", "Login Successful",
                    "token", token,
                    "role", role
            );

        } catch (Exception e) {

            // ❌ failed attempts logic
            int attempts = user.getFailedAttempts() + 1;
            user.setFailedAttempts(attempts);

            if (attempts >= 5) {
                user.setAccountLocked(true);
                user.setLockTime(LocalDateTime.now().plusMinutes(5));
            }

            userRepo.save(user);

            return Map.of(
                    "status", 401,
                    "error", "Invalid credentials",
                    "attempts", attempts
            );
        }
    }

    // ===============================
    // 🔐 SET PASSWORD USING TOKEN
    // ===============================
    public String setPassword(String token, String password) {

        Token tk = tokenRepo.findById(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (tk.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        if (tk.isUsed()) {
            throw new RuntimeException("Token already used");
        }

        String email = tk.getEmail();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);

        tk.setUsed(true);
        tokenRepo.save(tk);

        return "Password set successfully";
    }
}