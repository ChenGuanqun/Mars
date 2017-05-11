package com.mars.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by tachen on 2017/5/11.
 */
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(urlPatterns = {"/*"}, description = "Session Checker Filter")
public class AuthenticatedFilter implements Filter {

    @Autowired
    private HttpSession httpSession;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = ((HttpServletRequest) servletRequest).getHeader("X-Authenticated-Token");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }





}
