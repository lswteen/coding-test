package com.illuminarean.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class Jwt {

    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String BEARER = "Bearer ";

    public static final String HEADER_KEY = "Authorization";

    private final String secret;

    private final int expiration;

    public Jwt(String secret, int expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String create(JdbcAuthenticationToken authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining("|")))
                .claim("id", authentication.getId())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

    public JdbcAuthenticationToken convertToAuthentication(HttpServletRequest request) {
        return convertToAuthentication(obtainAuthorizationToken(request));
    }

    public JdbcAuthenticationToken convertToAuthentication(String token) {
        if(token == null) return null;

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return new JdbcAuthenticationToken(
                new JdbcAuthenticationPrincipal(claims.get("id", Long.class), claims.getSubject()),
                null,
                Arrays.stream(claims.get("authorities", String.class).split("\\|")).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }

    private String obtainAuthorizationToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_KEY);
        if (token != null) {
            try {
                token = URLDecoder.decode(token, StandardCharsets.UTF_8.name());
                String[] parts = token.split(" ");
                if (parts.length == 2) {
                    String scheme = parts[0];
                    String credentials = parts[1];
                    return BEARER.trim().equals(scheme) ? credentials : null;
                }
            } catch (UnsupportedEncodingException e) {
                log.error("unsupported charsets exception : {}", e.getMessage(), e);
            }
        }
        return null;
    }
}
