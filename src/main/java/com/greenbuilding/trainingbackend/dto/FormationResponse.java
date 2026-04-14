package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.TrainerType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// Outgoing payload that exposes formation data and a compact participant summary.
public record FormationResponse(
        Long id,
        String titre,
        Integer annee,
        Integer dureeJours,
        BigDecimal budget,
        String lieu,
        LocalDate dateFormation,
        Integer domaineId,
        String domaineLibelle,
        Long formateurId,
        String formateurNom,
        String formateurPrenom,
        TrainerType formateurType,
        List<ParticipantSummary> participants
) {
    // Small nested DTO used to avoid returning full participant entities.
    public record ParticipantSummary(
            Integer id,
            String nom,
            String prenom
    ) {
    }
}
