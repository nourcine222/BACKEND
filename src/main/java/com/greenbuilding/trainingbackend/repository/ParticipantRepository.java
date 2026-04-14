package com.greenbuilding.trainingbackend.repository;

import com.greenbuilding.trainingbackend.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    Optional<Participant> findByEmailIgnoreCase(String email);
}
