package com.example.esos.services;

import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.requests.IncidentUpdate;
import com.example.esos.models.responses.GenericResponse;
import com.example.esos.models.responses.IncidentResponse;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface IncidentService {

    //get incidents as per role
    ResponseEntity<IncidentResponse> getIncidents(String username);

    //create incident

    ResponseEntity<GenericResponse> createIncident(IncidentCreate incidentCreate);

    //update incident

    ResponseEntity<GenericResponse> updateIncident(IncidentUpdate incidentRequest);


}
