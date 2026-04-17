package com.noman.ems.common.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.noman.ems.common.entity.Token;
import com.noman.ems.common.repository.TokenRepository;
import com.noman.ems.user.entity.User;
import com.noman.ems.user.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ===============================
    // 🔐 SET PASSWORD USING TOKEN
    // ===============================
    @PostMapping("/set-password")
    public String setPassword(@RequestParam String token,
                              @RequestParam String password) {

        // 1. Token check
        Token tk = tokenRepo.findById(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        // 2. Expiry check
        if (tk.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        // 3. Already used
        if (tk.isUsed()) {
            throw new RuntimeException("Token already used");
        }

        String email = tk.getEmail();

        // 4. USER fetch
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 5. Password set
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);

        // 6. Mark token used
        tk.setUsed(true);
        tokenRepo.save(tk);

        return "Password set successfully";
    }

    // ===============================
    // 🔐 LOGIN (COMMON FOR ALL ROLES)
    // ===============================
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        // 🔒 Account lock check
        if (user.isAccountLocked()) {

            if (user.getLockTime().isAfter(LocalDateTime.now())) {
                throw new RuntimeException("Account locked! Try after 5 minutes");
            } else {
                user.setAccountLocked(false);
                user.setFailedAttempts(0);
            }
        }

        // 🔐 Password check
        if (passwordEncoder.matches(password, user.getPassword())) {

            user.setFailedAttempts(0);
            user.setAccountLocked(false);
            userRepo.save(user);

            return "Login Successful | Role: " + user.getRole();
        } else {

            int attempts = user.getFailedAttempts() + 1;
            user.setFailedAttempts(attempts);

            if (attempts >= 5) {
                user.setAccountLocked(true);
                user.setLockTime(LocalDateTime.now().plusMinutes(5));
            }

            userRepo.save(user);

            throw new RuntimeException("Invalid password. Attempts: " + attempts);
        }
    }
}