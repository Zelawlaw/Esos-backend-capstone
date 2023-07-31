package com.example.esos.services.impl;


import com.example.esos.dto.SignupRequest;
import com.example.esos.entities.User;
import com.example.esos.entities.UserPermission;
import com.example.esos.exceptions.UserNotFoundException;
import com.example.esos.models.Role;
import com.example.esos.models.responses.GenericResponse;
import com.example.esos.repositories.UserPermissionRepository;
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
    private final UserPermissionRepository userPermissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<GenericResponse> createUser(SignupRequest signupRequest) {

        try {
            User newuser;
            if (signupRequest.getManagerId()!= null)
            {
                User manager = this.userRepository.findUserById(signupRequest.getManagerId())
                                 .orElseThrow(()-> new UserNotFoundException("User with Id:"+signupRequest.getManagerId()+" not found"));
                newuser = new User( signupRequest.getUsername(), signupRequest.getPassword(),manager, passwordEncoder);
            }
            else {
                newuser = new User( signupRequest.getUsername(), signupRequest.getPassword(), passwordEncoder);
            }

            UserPermission userperm = new UserPermission(Role.valueOf(signupRequest.getRole()),newuser);
            newuser.setUserPermission(userperm);

            this.userRepository.save(newuser);
            this.userPermissionRepository.save(userperm);
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
