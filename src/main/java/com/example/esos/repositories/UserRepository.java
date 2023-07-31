package com.example.esos.repositories;

import com.example.esos.entities.Log;
import com.example.esos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findUserById(Integer id);

    Optional<User> findUserByUsername(String username);




}
