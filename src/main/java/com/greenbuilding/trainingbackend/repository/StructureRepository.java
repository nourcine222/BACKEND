package com.greenbuilding.trainingbackend.repository;

import com.greenbuilding.trainingbackend.entity.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository for Structure entities.
public interface StructureRepository extends JpaRepository<Structure, Integer> {
    Optional<Structure> findByLibelleIgnoreCase(String libelle);
}
