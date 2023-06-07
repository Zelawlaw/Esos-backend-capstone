package com.example.esos.services.impl;

import com.example.esos.entities.Incident;
import com.example.esos.entities.Log;
import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.requests.IncidentUpdate;
import com.example.esos.models.responses.GenericResponse;
import com.example.esos.models.responses.IncidentResponse;
import com.example.esos.repositories.IncidentRepository;
import com.example.esos.services.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

   private final IncidentRepository incidentRepository;

    @Override
    public ResponseEntity<IncidentResponse> getIncidents(String username) {
        return null;
    }

    @Override
    public ResponseEntity<GenericResponse> createIncident(IncidentCreate incidentCreate) {

       Incident reportedIncident = Incident.builder()
                       .incidentID("ESOS" + String.format("%03d", 0) + String.format("%04d", (int) (Math.random() * 10000))) //requires a proper method
                       .reporter("lolo")  //later get it from logged in user
                       .description(incidentCreate.getMessage())
                       .status("active")
                        .reportedtime(new Date())
                       .build();

       try{
           this.incidentRepository.save(reportedIncident);
           return new ResponseEntity<>(GenericResponse.builder()
                   .message("success")
                   .status("200")
                   .build(), HttpStatus.OK);
       }
       catch(Exception Ex){
           return new ResponseEntity<>(GenericResponse.builder()
                   .message("Error While saving")
                   .status("500")
                   .build(), HttpStatus.INTERNAL_SERVER_ERROR);
       }


    }

    @Override
    public ResponseEntity<GenericResponse> updateIncident(IncidentUpdate incidentRequest) {
        AtomicReference<ResponseEntity<GenericResponse>> response = new AtomicReference<>();

        this.incidentRepository.findUserByIncidentID(incidentRequest.getIncidentId())
                .ifPresentOrElse(incident -> {
                    try {
                        // Create new log
                        Log newLog = Log.builder()
                                .incidentUpdate(incidentRequest.getUpdate())
                                .incident(incident)
                                .build();

                        // Update logs
                        Collection<Log> logs = incident.getLogsCollection();
                        logs.add(newLog);
                        incident.setLogsCollection(logs);

                        // Save updated incident with logs
                        this.incidentRepository.save(incident);
                        response.set(ResponseEntity.ok()
                                .body(GenericResponse.builder()
                                        .message("success")
                                        .status("200")
                                        .build()));
                    } catch (Exception e) {
                        response.set(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body(new GenericResponse("500", "Error updating incident")));
                    }
                }, () -> {
                    response.set(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new GenericResponse("404", "Incident Not found")));
                });

        return response.get();
    }

}
