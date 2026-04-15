package com.noman.ems.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.noman.ems.employee.entity.Employee;
import com.noman.ems.employee.repository.EmployeeRepository;
import com.noman.ems.client.entity.Client;
import com.noman.ems.client.repository.ClientRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 🔴 ADMIN
        if (email.equals("admin@gmail.com")) {
            return new User(
                    "admin@gmail.com",
                    "$2a$10$Dow1z1H0wYF7vYpZp9tRjOeQwK6Hkz2z0l9QwYyYzX9G6yGZk8h2K",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // 🔵 Employee
        Employee emp = empRepo.findByEmail(email).orElse(null);
        if (emp != null) {
            return new User(
                    emp.getEmail(),
                    emp.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(emp.getRole()))
            );
        }

        // 🟢 Client
        Client client = clientRepo.findByEmail(email).orElse(null);
        if (client != null) {
            return new User(
                    client.getEmail(),
                    client.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(client.getRole()))
            );
        }

        throw new UsernameNotFoundException("User not found");
    }
}