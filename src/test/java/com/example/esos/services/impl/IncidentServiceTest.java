package com.example.esos.services.impl;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@Transactional
@SpringBootTest
class IncidentServiceTest {

    @Autowired
    IncidentService incidentService;

    @Autowired
    UserRepository userRepository;

    @SpyBean
    IncidentRepository incidentRepository;

    Incident incident1, incident2;
    Log log1, log2, log3, log4;

    User user1, user2;

    @BeforeEach
    void setUp() {
        user1 = new User("sample lolo", "password1");
        user2 = new User("sample ngururu", "password2");
        user1.setManager(user2);
        Collection<User> directreport = new ArrayList<>();
        directreport.add(user1);
        user2.setDirectReports(directreport);
        incident1 = new Incident("SOS23434", "heavy flu", new Date(), user1.getUsername());
        incident2 = new Incident("SOS23677", "severe headache", new Date(), user2.getUsername());
        log1 = new Log("gone to hospital", new Date(), "James");
        log2 = new Log("seen doctor", new Date(), "James");
        log3 = new Log("at pharmacy", new Date(), "Juliet");
        log4 = new Log("going home", new Date(), "Juliet");


    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testIncidentCreateSuccess() {

        //create IncidentCreate object
        IncidentCreate incidentCreate = new IncidentCreate("I am having a heavy flu. cannot leave home.");

        //create mockSavedIncident1
        Collection<Log> logs = new ArrayList<>();
        log1.setIncident(incident1);
        logs.add(log1);
        log2.setIncident(incident1);
        logs.add(log2);

        Incident mockSavedIncident1 = Incident.builder()
                .id(2345)
                .incidentID(incident1.getIncidentID())
                .description(incident1.getDescription())
                .incidentowner(incident1.getIncidentowner())
                // .logsCollection(logs)
                .reportedtime(incident1.getReportedtime())
                .reporter(incident1.getReporter())
                .status("active")
                .build();

        //when
        doReturn(mockSavedIncident1).when(incidentRepository).save(incident1);

        //then

        ResponseEntity<GenericResponse> response = this.incidentService.createIncident(incidentCreate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GenericResponse genericResponse = response.getBody();
        assertNotNull(genericResponse);
        String message = genericResponse.getMessage();
        String status = genericResponse.getStatus();
        assertNotNull(message);
        assertNotNull(status);
        assertEquals("success", message);
        assertEquals("200", status);

    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testIncidentUpdateSuccess() {
        //when

        //save incident in db
        //add previous logs to incident
        Collection<Log> logs = new ArrayList<>();
        log1.setIncident(incident1);
        logs.add(log1);
        log2.setIncident(incident1);
        logs.add(log2);
        incident1.setLogsCollection(logs);
        this.incidentRepository.save(incident1);

        //create IncidentCreate object
        IncidentUpdate incidentUpdate = new IncidentUpdate("Prescribed medication", incident1.getIncidentID());

        //then

        ResponseEntity<GenericResponse> response = this.incidentService.updateIncident(incidentUpdate);
        //check if logs increased to 3
        this.incidentRepository
                .findByIncidentID(incident1.getIncidentID())
                .ifPresent(updatedIncident -> {
                    assertEquals(3, updatedIncident.getLogsCollection().size());
                });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GenericResponse genericResponse = response.getBody();
        assertNotNull(genericResponse);
        String message = genericResponse.getMessage();
        String status = genericResponse.getStatus();
        assertNotNull(message);
        assertNotNull(status);
        assertEquals("success", message);
        assertEquals("200", status);
    }


    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testSuccessfulGetIncidents() {
        //save users
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        this.userRepository.saveAll(users);

        //save incidents
        Collection<Log> logs = new ArrayList<>();
        log1.setIncident(incident1);
        logs.add(log1);
        log2.setIncident(incident1);
        logs.add(log2);
        incident1.setLogsCollection(logs);

        Collection<Log> logs2 = new ArrayList<>();
        log3.setIncident(incident2);
        logs2.add(log3);
        log4.setIncident(incident2);
        logs2.add(log4);
        incident2.setLogsCollection(logs2);

        //save both incidents
        List<Incident> incidents = new ArrayList<>();
        incidents.add(incident1);
        incidents.add(incident2);
        this.incidentRepository.saveAll(incidents);

        ResponseEntity<IncidentResponse> response = this.incidentService.getIncidents(user2.getUsername());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        IncidentResponse incidentResponse = response.getBody();
        assertEquals(1, incidentResponse.getPersonalIncidents().size());


    }


    @AfterEach
    void tearDown() {
        //remove sample incidents
        this.incidentRepository.deleteByIncidentID(incident1.getIncidentID());
        this.incidentRepository.deleteByIncidentID(incident2.getIncidentID());

        //remove sample users
//        this.userRepository.deleteByUserId(user1.getUserId());
//        this.userRepository.deleteByUserId(user2.getUserId());
    }
}