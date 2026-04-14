package com.greenbuilding.trainingbackend.repository;

import com.greenbuilding.trainingbackend.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation, Long> {
    Optional<Formation> findByTitreIgnoreCaseAndAnnee(String titre, Integer annee);
}
