package com.example.esos.services.impl;

import com.example.esos.entities.Incident;
import com.example.esos.entities.Log;
import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.responses.GenericResponse;
import com.example.esos.models.responses.IncidentResponse;
import com.example.esos.repositories.IncidentRepository;
import com.example.esos.services.IncidentService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class IncidentServiceTest {

    @Autowired
    IncidentService incidentService;

    @MockBean
    IncidentRepository incidentRepository;

    Incident incident1,incident2;
    Log log1,log2;

    @BeforeEach
    void setUp() {

        incident1 = new Incident("SOS23434","heavy flu", new Date(),"lolo");
        incident2 = new Incident("SOS23677","severe headache", new Date(),"ngururu");
        log1 = new Log("gone to hospital",new Date(),"James");
        log2 = new Log("seen doctor",new Date(),"James");
    }

    @Test
    void testIncidentCreateSuccess(){

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
    assertEquals(HttpStatus.OK,response.getStatusCode());
        GenericResponse genericResponse = response.getBody();
        assertNotNull(genericResponse); // Make sure the response body is not null
        String message = genericResponse.getMessage();
        String status = genericResponse.getStatus();
        assertNotNull(message);
        assertNotNull(status);
        assertEquals("success",message);
        assertEquals("200",status);

    }

    @AfterEach
    void tearDown() {
    }
}