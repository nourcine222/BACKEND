package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.TrainerType;

// Outgoing payload that exposes trainer data to the API client.
public record FormateurResponse(
        Long id,
        String nom,
        String prenom,
        String email,
        String tel,
        TrainerType type,
        Integer employeurId,
        String employeurNom
) {
}
