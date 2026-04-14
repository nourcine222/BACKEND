package com.greenbuilding.trainingbackend.repository;

import com.greenbuilding.trainingbackend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

// Repository for AppUser entities and login-based lookups.
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByLogin(String login);

    @EntityGraph(attributePaths = "role")
    Optional<AppUser> findWithRoleByLogin(String login);
}
