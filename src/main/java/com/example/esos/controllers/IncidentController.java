package com.example.esos.controllers;


import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.requests.IncidentUpdate;
import com.example.esos.services.IncidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IncidentController {

private final IncidentService incidentService;

    @GetMapping("/v1/getincidents")
    public ResponseEntity getIncidents(@RequestParam String username){
        log.info("username {}",username);
        return this.incidentService.getIncidents(username);
    }


    @PostMapping("/v1/createincident")
    public ResponseEntity createIncident(@Valid @RequestBody IncidentCreate incidentCreate){
        log.info("incident to create {}",incidentCreate.toString());
        return this.incidentService.createIncident(incidentCreate);
    }

    @PostMapping("/v1/updateincident")
    public ResponseEntity updateIncident(@Valid @RequestBody IncidentUpdate incidentUpdate){
        log.info("incident to create {}",incidentUpdate.toString());
        return this.incidentService.updateIncident(incidentUpdate);
    }


}
