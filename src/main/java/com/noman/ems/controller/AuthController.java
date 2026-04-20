package com.noman.ems.controller;

//import com.noman.ems.service.SessionService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.noman.ems.entity.Token;
import com.noman.ems.repository.TokenRepository;
import com.noman.ems.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.noman.ems.entity.User;
import com.noman.ems.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {



	@Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthService authService;



    // ===============================
    // 🔐 SET PASSWORD USING TOKEN
    // ===============================
    @PostMapping("/set-password")
    public String setPassword(@RequestParam String token,
                              @RequestParam String password) {

        return authService.setPassword(token, password);
    }

   

    
    
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        request.getSession().invalidate(); // session delete 
        SecurityContextHolder.clearContext(); //spring clear

       

        return "Logout successful";
    }
}





