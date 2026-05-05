package com.noman.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import com.noman.ems.service.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;




@RestController
@RequestMapping("/auth")
@SecurityRequirement(name = "bearerAuth")
public class AuthController {
	
	@Autowired
	private JavaMailSender mailSender;
	
    @Autowired
    private AuthService authService;

    // ===============================
    // 🔐 LOGIN (JWT)
    // ===============================
    @PostMapping("/login")
    public Object login(@RequestParam String email,
                        @RequestParam String password) {
        return authService.login(email, password);
    }

    // ===============================
    // 🔐 SET PASSWORD
    // ===============================
    @PostMapping("/set-password")
    public String setPassword(@RequestParam String token,
                              @RequestParam String password) {
        return authService.setPassword(token, password);
    }

    // ===============================
    // 🔓 LOGOUT (JWT)
    // ===============================
    @PostMapping("/logout")
    public String logout() {
        return "Logout successful (client side token delete karo)";
    }
    
    @GetMapping("/mail-test")
    public String testMail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("nomanqureshi2204email@gmail.com");
        msg.setFrom("nomanqureshi2204email@gmail.com");
        msg.setSubject("Test");
        msg.setText("Hello");

        mailSender.send(msg);

        return "sent";
    }
}