package com.example.esos.repositories;

import com.example.esos.entities.Incident;
import com.example.esos.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log,Integer> {
}
