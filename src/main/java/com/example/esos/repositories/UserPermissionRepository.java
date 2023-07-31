package com.example.esos.repositories;

import com.example.esos.entities.User;
import com.example.esos.entities.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPermissionRepository extends JpaRepository<UserPermission,Integer> {

}
