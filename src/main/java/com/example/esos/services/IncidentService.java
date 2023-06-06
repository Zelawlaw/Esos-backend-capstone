package com.example.esos.services;

import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.requests.IncidentUpdate;
import com.example.esos.models.responses.GenericResponse;
import com.example.esos.models.responses.IncidentResponse;

import java.util.List;

public interface IncidentService {

    //get incidents as per role
    List<IncidentResponse> getIncidents();

    //create incident

    List<GenericResponse> createIncident (IncidentCreate incidentCreate);

    //update incident

    List<GenericResponse> updateIncident( IncidentUpdate incidentRequest);



}
