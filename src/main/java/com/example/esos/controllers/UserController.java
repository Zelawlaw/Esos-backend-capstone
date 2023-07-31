package com.example.esos.controllers;


import com.example.esos.dto.SignupRequest;
import com.example.esos.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/createuser")
    public ResponseEntity createUser (@Valid @RequestBody SignupRequest newuser) {
        try {
           return this.userService.createUser(newuser);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping("/v1/getusers")
    public ResponseEntity getUsers (){
        return this.userService.getUsers();
    }
}
