package com.example.esos.controllers;


import com.example.esos.config.JwtTokenProvider;
import com.example.esos.dto.AuthenticationResponse;
import com.example.esos.dto.LoginRequest;
import com.example.esos.services.impl.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/v1/authenticate")
    public ResponseEntity authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("in authenticate controller");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            log.info("userDetails :"+authentication.getName()+ " is authenticated :"+authentication.isAuthenticated());
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            String token = jwtTokenProvider.createToken(userDetails);
            long expiryInMinutes = jwtTokenProvider.getExpiryInMinutes();

            AuthenticationResponse response = new AuthenticationResponse(token, expiryInMinutes+" Minutes");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            log.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }


}
