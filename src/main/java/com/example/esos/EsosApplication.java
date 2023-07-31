package com.example.esos;

import com.example.esos.entities.Incident;
import com.example.esos.entities.Log;
import com.example.esos.repositories.IncidentRepository;
import com.example.esos.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
public class EsosApplication {


    @Autowired
    IncidentRepository incidentRepository;

    @Autowired
    LogRepository logRepository;

    public static void main(String[] args) {
        SpringApplication.run(EsosApplication.class, args);
    }


}
