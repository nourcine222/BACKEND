package com.greenbuilding.trainingbackend.repository;

import com.greenbuilding.trainingbackend.entity.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository for Formateur entities.
public interface FormateurRepository extends JpaRepository<Formateur, Long> {
    Optional<Formateur> findByEmailIgnoreCase(String email);
}
