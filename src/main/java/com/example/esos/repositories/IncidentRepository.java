package com.example.esos.repositories;

import com.example.esos.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository  extends JpaRepository<Incident,Integer> {
}
