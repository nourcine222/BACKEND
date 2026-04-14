package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.TrainerType;

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
