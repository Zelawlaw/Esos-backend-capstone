package com.example.esos.repositories;

import com.example.esos.entities.Incident;
import com.example.esos.entities.Log;
import com.example.esos.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class IncidentRepositoryTest {

    @Autowired
    IncidentRepository incidentRepository;

    @Autowired
    LogRepository logRepository;
    Incident incident1,incident2;
    Log log1,log2,log3,log4;
    @BeforeEach
    void setUp() {
        Collection<Log> logs1 = new ArrayList<>();
        Collection<Log> logs2 = new ArrayList<>();

        incident1 = new Incident("SOS23434","heavy flu", new Date(),"lolo");
        incident2 = new Incident("SOS23677","severe headache", new Date(),"ngururu");
        log1 = new Log("gone to hospital",new Date(),"James");
        log1.setIncident(incident1);
        logs1.add(log1);
        log2 = new Log("seen doctor",new Date(),"James");
        log2.setIncident(incident1);
        logs1.add(log2);
        incident1.setLogsCollection(logs1);
//        log3 = new Log("at pharmacy",new Date(), "Juliet");
//        log3.setIncident(incident2);
//        logs2.add(log3);
//        log4 = new Log("going home",new Date(), "Juliet");
//        log4.setIncident(incident2);
//        logs2.add(log4);
//        incident2.setLogsCollection(logs2);
    }

    @Test
    void testSuccessfulSaving(){
        this.incidentRepository.save(incident1);
        Optional<Incident> fetchedUser = this.incidentRepository.findUserByIncidentID(incident1.getIncidentID());
        assertEquals(incident1.getIncidentID(),fetchedUser.orElse(null).getIncidentID());
    }


    @Test
    void testSuccessfulSavingWithLogs(){

        this.incidentRepository.save(incident1);
        Optional<Incident> fetchedIncident = this.incidentRepository.findUserByIncidentID(incident1.getIncidentID());
        assertEquals(2,fetchedIncident.orElse(null).getLogsCollection().size());
    }

    @AfterEach
    void tearDown() {
       this.incidentRepository.deleteByIncidentID(incident1.getIncidentID());
    }

}