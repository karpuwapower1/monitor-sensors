package com.lab.test.task.monitorsensors.security;


import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_PARAMETER = "token";

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
        val jwtToken = getJwtTokenFromHeader(request);

        if (StringUtils.isNotBlank(jwtToken)) {
            val username = jwtTokenProvider.getUsernameFromToken(jwtToken);
            val context = SecurityContextHolder.getContext();
            if (Objects.nonNull(username) && Objects.nonNull(context.getAuthentication())) {
                val userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtTokenProvider.validateToken(jwtToken, userDetails)) {
                    context.setAuthentication(toAuthenticationToken(userDetails, request));
                }
            }
        }
        chain.doFilter(request, response);
    }

    private String getJwtTokenFromHeader(HttpServletRequest request) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return header != null && header.startsWith(BEARER_PREFIX)
            ? header.substring(BEARER_PREFIX.length())
            : request.getParameter(AUTHORIZATION_PARAMETER);
    }

    private UsernamePasswordAuthenticationToken toAuthenticationToken(UserDetails details, HttpServletRequest request) {
        val authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}

