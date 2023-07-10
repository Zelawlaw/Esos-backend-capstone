package com.example.esos.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
    }

    public JwtAuthenticationToken(Object principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.setAuthenticated(true);
        this.setDetails(principal);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return this.getDetails();
    }
}
