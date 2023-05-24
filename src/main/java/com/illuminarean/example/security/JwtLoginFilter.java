package com.illuminarean.example.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.illuminarean.example.security.dto.LoginRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final Jwt jwt;

    public JwtLoginFilter(String url, AuthenticationManager authManager, Jwt jwt) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.jwt = jwt;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        LoginRequestDto req = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
        return getAuthenticationManager().authenticate(
                new JdbcAuthenticationToken(
                        req.getEmail(), req.getPassword(), Lists.newArrayList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        String token = jwt.create((JdbcAuthenticationToken) authentication);
        response.setHeader(Jwt.HEADER_KEY, Jwt.BEARER + token);
        response.getOutputStream().print(token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().print(failed.getClass().getSimpleName());
    }
}
