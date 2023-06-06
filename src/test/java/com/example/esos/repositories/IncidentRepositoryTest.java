package com.example.esos.repositories;

import com.example.esos.entities.Incident;
import com.example.esos.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class IncidentRepositoryTest {

    @Autowired
    IncidentRepository incidentRepository;        
    Incident incident1,incident2;
//    public Incident( String incidentID,String  description, Date reportedtime,String reporter)
    @BeforeEach
    void setUp() {
        incident1 = new Incident("SOS23434","heavy flu", new Date(),"lolo");
        incident2 = new Incident("SOS23677","severe headache", new Date(),"ngururu");

    }

    @Test
    void testSuccessfulSaving(){
        this.incidentRepository.save(incident1);
        Optional<Incident> fetchedUser = this.incidentRepository.findUserByIncidentID(incident1.getIncidentID());
        assertEquals(incident1.getIncidentID(),fetchedUser.orElse(null).getIncidentID());
    }


    @AfterEach
    void tearDown() {
       this.incidentRepository.deleteByIncidentID(incident1.getIncidentID());
    }

}