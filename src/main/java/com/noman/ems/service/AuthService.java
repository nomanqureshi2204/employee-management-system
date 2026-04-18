package com.noman.ems.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noman.ems.entity.Token;
import com.noman.ems.repository.TokenRepository;
import com.noman.ems.entity.User;
import com.noman.ems.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired 
    private TokenRepository tokenRepo;
    
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

    public Object login(String email, String password) {
    	
    	System.out.println("######################"+email);
    	System.out.println(userRepo.findByEmail(email));
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email nanan"));
        
        // 🔒 lock check
        if (user.isAccountLocked()) {
            if (user.getLockTime().isAfter(LocalDateTime.now())) {
                throw new RuntimeException("Account locked! Try after 5 minutes");
            } else {
                user.setAccountLocked(false);
                user.setFailedAttempts(0);
            }
        }

        // 🔐 password check
        if (passwordEncoder.matches(password, user.getPassword())) {

            user.setFailedAttempts(0);
            user.setAccountLocked(false);
            userRepo.save(user);

            return Map.of(
                "message", "Login Successful",
                "role", user.getRole(),
                "email", user.getEmail()
            );

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