package com.example.esos.services;

import com.example.esos.dto.LoginRequest;
import com.example.esos.dto.SignupRequest;
import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.requests.IncidentUpdate;
import com.example.esos.models.responses.GenericResponse;
import com.example.esos.models.responses.IncidentResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {


    ResponseEntity<GenericResponse>  createUser(SignupRequest signupRequest);

}
