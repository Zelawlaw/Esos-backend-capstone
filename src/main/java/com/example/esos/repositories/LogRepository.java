package com.example.esos.repositories;

import com.example.esos.entities.Incident;
import com.example.esos.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Integer> {

    Optional<List<Log>> findLogByIncident(Incident incident);

    void deleteByIncident(Incident incident);
}
