package com.mars.filter;

import com.mars.common.ParamConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tachen on 2017/5/11.
 */
@WebFilter(urlPatterns = { "/api/*" }, description = "Session Checker Filter")
public class AuthenticatedFilter implements Filter {

    @Autowired
    private HttpSession httpSession;

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
        Arrays.asList("/api", "/api/user", "/api/user/session")));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path) ;
        if (allowedPath) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String token = request.getHeader(ParamConstants.X_AUTHENTICATED_TOKEN);
            String sessionId = (String) httpSession.getServletContext().getAttribute(ParamConstants.SESSION_ID);
            if (StringUtils.isEmpty(token) || !token.equals(sessionId)) {
                response.reset();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"The token is invalid, it may be expired!");
            }else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }

}
