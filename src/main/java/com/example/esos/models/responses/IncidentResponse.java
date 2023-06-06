package com.example.esos.models.responses;

import com.example.esos.entities.Incident;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentResponse {

    //user incidents

    List<Incident> personalIncidents;


    //if user is a manager
    List<Incident> reporteeIncidents;


    //if user is an Incident Manager
    List<Incident> imIncidents;
}
