package com.example.esos.repositories;

import com.example.esos.entities.Log;
import com.example.esos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
