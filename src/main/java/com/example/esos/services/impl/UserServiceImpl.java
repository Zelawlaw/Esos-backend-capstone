package com.example.esos.services.impl;

import com.example.esos.dto.LoginRequest;
import com.example.esos.entities.User;
import com.example.esos.models.responses.GenericResponse;
import com.example.esos.repositories.UserRepository;
import com.example.esos.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<GenericResponse> createUser(LoginRequest loginRequest) {

        try {
            User newuser = new User(1, loginRequest.getUsername(), loginRequest.getPassword(), passwordEncoder);
            this.userRepository.save(newuser);

            return new ResponseEntity<>(GenericResponse.builder()
                    .message("success")
                    .status("200")
                    .build(), HttpStatus.OK);
        } catch (Exception Ex) {
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("Error While saving")
                    .status("500")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
