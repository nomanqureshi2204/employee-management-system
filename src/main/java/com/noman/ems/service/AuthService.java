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

    
}