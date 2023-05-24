package com.illuminarean.example.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JdbcAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private final String credentials;

    JdbcAuthenticationToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);

        this.principal = principal;
        this.credentials = credentials;
    }

    public static JdbcAuthenticationToken of(Claims claims) {
        return null;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public String getName() {
        return ((JdbcAuthenticationPrincipal) this.getPrincipal()).getName();
    }

    public Long getId() {
        return ((JdbcAuthenticationPrincipal) this.getPrincipal()).getId();
    }
}
