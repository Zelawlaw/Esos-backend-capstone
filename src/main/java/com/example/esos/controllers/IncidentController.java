package com.example.esos.controllers;


import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.requests.IncidentUpdate;
import com.example.esos.services.IncidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    @GetMapping("/v1/getincidents")
    public ResponseEntity getIncidents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("username {}", username);
        return this.incidentService.getIncidents(username);
    }


    @PostMapping("/v1/createincident")
    public ResponseEntity createIncident(@Valid @RequestBody IncidentCreate incidentCreate, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        log.info("incident to create {}", incidentCreate.toString());
        return this.incidentService.createIncident(incidentCreate);
    }

    @PostMapping("/v1/updateincident")
    public ResponseEntity updateIncident(@Valid @RequestBody IncidentUpdate incidentUpdate) {
        log.info("incident to create {}", incidentUpdate.toString());
        return this.incidentService.updateIncident(incidentUpdate);
    }


}
