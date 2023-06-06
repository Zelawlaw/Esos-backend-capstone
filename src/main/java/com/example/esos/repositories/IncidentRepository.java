package com.example.esos.repositories;

import com.example.esos.entities.Incident;
import com.example.esos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncidentRepository  extends JpaRepository<Incident,Integer> {


    Optional<Incident> findUserByIncidentID(String incidentId);

    void deleteByIncidentID(String  incidentId);
}
