package com.example.esos.config;

import com.example.esos.services.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public class EsosAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("in esos authentication provider");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            String token = jwtTokenProvider.createToken(userDetails);
            return new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Invalid username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
