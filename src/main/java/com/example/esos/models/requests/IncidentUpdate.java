package com.example.esos.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncidentUpdate {

    String update;
    String incidentId;
}
