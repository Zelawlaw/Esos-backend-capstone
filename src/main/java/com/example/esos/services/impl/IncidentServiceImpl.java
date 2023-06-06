package com.example.esos.services.impl;

import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.requests.IncidentUpdate;
import com.example.esos.models.responses.GenericResponse;
import com.example.esos.models.responses.IncidentResponse;
import com.example.esos.services.IncidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class IncidentServiceImpl implements IncidentService {
    @Override
    public ResponseEntity<IncidentResponse> getIncidents() {
        return null;
    }

    @Override
    public ResponseEntity<GenericResponse> createIncident(IncidentCreate incidentCreate) {
        return null;
    }

    @Override
    public ResponseEntity<GenericResponse> updateIncident(IncidentUpdate incidentRequest) {
        return null;
    }
}
