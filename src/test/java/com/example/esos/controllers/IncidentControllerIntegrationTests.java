package com.example.esos.controllers;

import com.example.esos.dto.IncidentSummary;
import com.example.esos.entities.Incident;
import com.example.esos.entities.Log;
import com.example.esos.entities.User;
import com.example.esos.models.requests.IncidentCreate;
import com.example.esos.models.requests.IncidentUpdate;
import com.example.esos.repositories.IncidentRepository;
import com.example.esos.repositories.UserRepository;
import com.example.esos.services.IncidentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IncidentControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentRepository incidentRepository;

    @MockBean
    private UserRepository userRepository;

    @SpyBean
    private IncidentService incidentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testGetIncidents() throws Exception {
        String username = "testUser";
        List<Incident> mockIncidents = Arrays.asList(
             new Incident("SOS23434", "heavy flu", new Date(), username),
       new Incident("SOS23677", "severe headache", new Date(),username));

        User testuser = new User("testUser","sfdlkjslakdfj",passwordEncoder);

    //    when
        doReturn(Optional.of(testuser)).when(userRepository).findUserByUsername(any(String.class));
        doReturn(Optional.of(mockIncidents)).when(incidentRepository).findByReporter(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getincidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(username)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testGetIncidentSummary() throws Exception {
        String username = "testUser";
        Incident mockIncident1 = new Incident("SOS23434", "heavy flu", new Date(), username);
        mockIncident1.setStatus("active");
        Incident mockIncident2 = new Incident("SOS23677", "severe headache", new Date(),username);
        mockIncident2.setStatus("active");
        Incident mockIncident3 = new Incident("SOS23678", "gun at caffeteria unattendedn", new Date(),username);
        mockIncident3.setStatus("pending");
        Incident mockIncident4 = new Incident("SOS23679", "Unknown individual roaming around", new Date(),username);
        mockIncident4.setStatus("resolved");
        List<Incident> mockIncidents = Arrays.asList(mockIncident1,mockIncident2,mockIncident3,mockIncident4);

        IncidentSummary incidentSummary = IncidentSummary.builder()
                .all(4)
                .active(2)
                .pending(1)
                .resolved(1)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(incidentSummary);
        User testuser = new User("testUser","sfdlkjslakdfj",passwordEncoder);

        //    when
        doReturn(Optional.of(testuser)).when(userRepository).findUserByUsername(any(String.class));
        doReturn(Optional.of(mockIncidents)).when(incidentRepository).findByReporter(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getincidentsummary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user(username)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

    }


    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testCreateIncident() throws Exception {
        IncidentCreate incidentCreate = new IncidentCreate("severe headache");
        Incident mockIncident = new Incident("SOS23677", "severe headache", new Date(),"testUser");

        //    when
        doReturn(mockIncident).when(incidentRepository).save(any(Incident.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/createincident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incidentCreate)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testUpdateIncident() throws Exception {
        IncidentUpdate incidentUpdate = new IncidentUpdate("getting better now","SOS23677");
        Incident mockIncident = new Incident("SOS23677", "severe headache", new Date(),"testUser");
        mockIncident.setLogsCollection(new ArrayList<>());
        Incident mockreturnedIncident = new Incident("SOS23677", "severe headache", new Date(),"testUser");
        Log newlog = new Log("getting better now",new Date(),"testuser");
        mockreturnedIncident.setLogsCollection(Arrays.asList(newlog));
//    when

        doReturn(Optional.of(mockIncident)).when(incidentRepository).findByIncidentID(any(String.class));
        doReturn(mockreturnedIncident).when(incidentRepository).save(any(Incident.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/updateincident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incidentUpdate)))
                .andExpect(status().isOk());
    }
}
