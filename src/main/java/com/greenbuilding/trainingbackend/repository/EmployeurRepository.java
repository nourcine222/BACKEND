package com.greenbuilding.trainingbackend.repository;

import com.greenbuilding.trainingbackend.entity.Employeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository for Employeur entities.
public interface EmployeurRepository extends JpaRepository<Employeur, Integer> {
    Optional<Employeur> findByNomEmployeurIgnoreCase(String nomEmployeur);
}
