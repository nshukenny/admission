package com.auca.service;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/process_admission")
public class AuthorizationFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("authenticatedUser") == null) {
            
            httpResponse.sendRedirect("login.html");
        } else {
            
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
       
    }

    public void destroy() {
        
    }
}
