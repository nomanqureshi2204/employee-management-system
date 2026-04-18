//package com.noman.ems.security;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import com.noman.ems.entity.User;
//import com.noman.ems.repository.UserRepository;
//
//@Component
//public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        AuthenticationException exception)
//            throws IOException, ServletException {
//
//        String email = request.getParameter("username");
//
//        User user = userRepo.findByEmail(email).orElse(null);
//
//        if (user != null) {
//
//            int attempts = user.getFailedAttempts() + 1;
//            user.setFailedAttempts(attempts);
//
//            if (attempts >= 5) {
//                user.setAccountLocked(true);
//                user.setLockTime(LocalDateTime.now().plusMinutes(5));
//            }
//
//            userRepo.save(user);
//        }
//    }
//}