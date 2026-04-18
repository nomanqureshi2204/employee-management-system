//package com.noman.ems.security;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import com.noman.ems.entity.User;
//import com.noman.ems.repository.UserRepository;
//
//@Component
//public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication)
//            throws IOException, ServletException {
//
//        String email = authentication.getName();
//
//        User user = userRepo.findByEmail(email).get();
//
//        user.setFailedAttempts(0);
//        user.setAccountLocked(false);
//        user.setLockTime(null);
//
//        userRepo.save(user);
//    }
//}