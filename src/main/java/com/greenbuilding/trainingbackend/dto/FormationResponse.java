package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.TrainerType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    public record ParticipantSummary(
            Integer id,
            String nom,
            String prenom
    ) {
    }
}
