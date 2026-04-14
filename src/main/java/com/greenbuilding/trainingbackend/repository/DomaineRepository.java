package com.greenbuilding.trainingbackend.repository;

import com.greenbuilding.trainingbackend.entity.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DomaineRepository extends JpaRepository<Domaine, Integer> {
    Optional<Domaine> findByLibelleIgnoreCase(String libelle);
}
