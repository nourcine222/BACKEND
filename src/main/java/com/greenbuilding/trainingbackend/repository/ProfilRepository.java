package com.greenbuilding.trainingbackend.repository;

import com.greenbuilding.trainingbackend.entity.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilRepository extends JpaRepository<Profil, Integer> {
    Optional<Profil> findByLibelleIgnoreCase(String libelle);
}
