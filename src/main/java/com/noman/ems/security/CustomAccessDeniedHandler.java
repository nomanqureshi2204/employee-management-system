package com.noman.ems.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        String uri = request.getRequestURI();

        String message = "ACCESS DENIED";

        // 🔥 URI ke hisaab se message set karo
        if (uri.startsWith("/admin") || uri.startsWith("/projects")) {
            message = "Only ADMIN can access this API";
        } 
        else if (uri.startsWith("/employees")) {
            message = "Only EMPLOYEE or ADMIN  can access this API";
        } 
        else if (uri.startsWith("/clients")) {
            message = "Only CLIENT or ADMIN can access this API";
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        response.getWriter().write(
            "{ \"status\": 403, \"error\": \"" + message + "\" }"
        );
    }
}