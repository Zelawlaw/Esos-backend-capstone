package com.example.esos.services.impl;

import com.example.esos.dto.IncidentSummary;
import com.example.esos.entities.Incident;
import com.example.esos.entities.Log;
import com.example.esos.entities.User;
import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.requests.IncidentUpdate;
import com.example.esos.models.responses.GenericResponse;
import com.example.esos.models.responses.IncidentResponse;
import com.example.esos.repositories.IncidentRepository;
import com.example.esos.repositories.UserRepository;
import com.example.esos.services.IncidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @Override
    public ResponseEntity<IncidentResponse> getIncidents(String username) {
        try {


            List<Incident> directReportsIncidents = new ArrayList<>();

            // Create an empty incident response
            IncidentResponse incidentResponse = new IncidentResponse();
            // Fetch user from username
            Optional<User> fetchedUser = this.userRepository.findUserByUsername(username);

            fetchedUser.ifPresent(user -> {
                // User is present
                // Find all personal incidents

                incidentResponse.setPersonalIncidents(this.incidentRepository.findByReporter(user.getUsername()).orElse(Collections.emptyList()));

                if (user.getDirectReports() != null) {
                    for (User directReport : user.getDirectReports()) {
                        this.incidentRepository.findByReporter(directReport.getUsername())
                                .ifPresent(directReportsIncidents::addAll);
                    }
                }
            });

            // Add an empty ArrayList if the user is absent
            if (!fetchedUser.isPresent()) {
                incidentResponse.setPersonalIncidents(Collections.emptyList());
            }
            incidentResponse.setReporteeIncidents(directReportsIncidents);

            //add im incidents later

            return ResponseEntity.ok(incidentResponse);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @Override
    public ResponseEntity<GenericResponse> createIncident(IncidentCreate incidentCreate) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Incident reportedIncident = Incident.builder()
                .incidentID("ESOS" + String.format("%03d", 0) + String.format("%04d", (int) (Math.random() * 10000))) //requires a proper method
                .reporter(username)  //getting it from logged in user
                .description(incidentCreate.getMessage())
                .status("active")
                .reportedtime(new Date())
                .build();

        try {
            this.incidentRepository.save(reportedIncident);
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

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @Override
    public ResponseEntity<GenericResponse> updateIncident(IncidentUpdate incidentRequest) {
        AtomicReference<ResponseEntity<GenericResponse>> response = new AtomicReference<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        this.incidentRepository.findByIncidentID(incidentRequest.getIncidentId())
                .ifPresentOrElse(incident -> {
                    try {
                        // Create new log
                        Log newLog = Log.builder()
                                .incidentUpdate(incidentRequest.getUpdate())
                                .incident(incident)
                                .updatedby(username)
                                .updatetime(new Date())
                                .build();

                        // Update logs
                        Collection<Log> logs = incident.getLogsCollection();
                        logs.add(newLog);
                        incident.setLogsCollection(logs);

                       ;
                    log.info("incidentRequest.getStatus() :{},incident.getStatus():{}",incidentRequest.getStatus(),incident.getStatus());
                     //check if incidentRequest has status and if status is different from current.
                    if(incidentRequest.getStatus()!= null  && incident.getStatus() != "resolved"
                      && incidentRequest.getStatus() != incident.getStatus())
                    {
                        log.info("I am here?");
                     incident.setStatus(incidentRequest.getStatus());
                    }
                        // Save updated incident with logs
                        this.incidentRepository.save(incident);
                        response.set(ResponseEntity.ok()
                                .body(GenericResponse.builder()
                                        .message("success")
                                        .status("200")
                                        .build()));
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        response.set(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body(new GenericResponse("500", "Error updating incident")));
                    }
                }, () -> {
                    response.set(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new GenericResponse("404", "Incident Not found")));
                });

        return response.get();
    }

    @Override
    public ResponseEntity<IncidentSummary> getIncidentSummary(String username) {
        try {

            List<Incident> allIncidents = new ArrayList<>();

            // Fetch user from username
            Optional<User> fetchedUser = this.userRepository.findUserByUsername(username);

            fetchedUser.ifPresent(user -> {
                // User is present
                // Find all personal incidents

                allIncidents.addAll(this.incidentRepository.findByReporter(user.getUsername()).orElse(Collections.emptyList()));

                if (user.getDirectReports() != null) {
                    for (User directReport : user.getDirectReports()) {
                        this.incidentRepository.findByReporter(directReport.getUsername())
                                .ifPresent(allIncidents::addAll);
                    }
                }
            });

            int all = allIncidents.size();
            AtomicInteger active = new AtomicInteger();
            AtomicInteger pending = new AtomicInteger();
            AtomicInteger resolved = new AtomicInteger();

            allIncidents.forEach(ticket -> {
                String status = ticket.getStatus().toLowerCase();
                if ("active".equals(status)) {
                    active.incrementAndGet();
                } else if ("pending".equals(status)) {
                    pending.incrementAndGet();
                } else if ("resolved".equals(status)) {
                    resolved.incrementAndGet();
                }
            });

            return ResponseEntity.ok(new IncidentSummary(all,active.get(),pending.get(),resolved.get()));
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
