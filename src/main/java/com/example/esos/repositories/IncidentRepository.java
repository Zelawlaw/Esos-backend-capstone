package com.example.esos.repositories;

import com.example.esos.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository  extends JpaRepository<Incident,Integer> {


    Optional<Incident> findByIncidentID(String incidentId);
    Optional<List<Incident>> findByReporter(String reporter);
    void deleteByIncidentID(String  incidentId);
}
