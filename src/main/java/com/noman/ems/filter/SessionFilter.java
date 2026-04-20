//package com.noman.ems.filter;
//
//import java.io.IOException;
//
//import org.springframework.stereotype.Component;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//@Component
//public class SessionFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request,
//                         ServletResponse response,
//                         FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        String uri = req.getRequestURI();
//
//        // allow login APIs
//        if (uri.startsWith("/auth") ||
//        uri.startsWith("/swagger")  ||
//        uri.startsWith("/v3/api-docs"))
//        {
//            chain.doFilter(request, response);
//            return;
//        }
//        
//        HttpSession session = req.getSession(false);
//        
//        // not logged in
//        if(session == null || session.getAttribute("user") == null){
//        	res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        	res.getWriter().write("LOGIN REQUIRED");
//        	return ;
//        }
//        
//        String role = (String)session.getAttribute("role");
//        
//        //role based control 
//        
//        if(role.equals("ROLE_ADMIN")) {
//        	chain.doFilter(request, response);
//        	return;
//        }
//        
//        if(uri.startsWith("/employees") && !role.equals("ROLE_EMPLOYEE")) {
//        	res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        	res.getWriter().write("ADMIN OR EMPLOYEE ONLY ACCESS");
//        	return;
//        }
//        
//        if(uri.startsWith("/clients") && !role.equals("ROLE_CLIENT")) {
//        	res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        	res.getWriter().write("ADMIN OR CLIENT ONLY ACCESS");
//        	return ;
//        }
//        
//        if(uri.startsWith("/projects") && !role.equals("ROLE_ADMIN")) {
//        	res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        	res.getWriter().write("ADMIN ONLY ACCESS");
//        	return ;
//        }
//        
//        chain.doFilter(request, response);
//     
//    }
//}
//
//
//
//
//
