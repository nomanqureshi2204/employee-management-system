package com.noman.ems.security;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.noman.ems.entity.User;
import com.noman.ems.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Lock Check 
        if(user.isAccountLocked()) {
        	if(user.getLockTime().isAfter(LocalDateTime.now())) {
        		throw new RuntimeException("Account locked! Try later");
        	}else {
        		user.setAccountLocked(false);
        		user.setFailedAttempts(0);
        		userRepo.save(user);
        	}
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}