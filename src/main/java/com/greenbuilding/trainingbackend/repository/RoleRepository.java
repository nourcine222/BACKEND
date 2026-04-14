package com.greenbuilding.trainingbackend.repository;

import com.greenbuilding.trainingbackend.entity.Role;
import com.greenbuilding.trainingbackend.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository for Role entities.
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName name);
}
