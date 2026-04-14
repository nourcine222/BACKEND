package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.TrainerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Incoming payload used to create or update a trainer.
public record FormateurRequest(
        @NotBlank
        @Size(max = 100)
        String nom,

        @NotBlank
        @Size(max = 100)
        String prenom,

        @Email
        @Size(max = 150)
        String email,

        @Size(max = 20)
        String tel,

        @NotNull
        TrainerType type,

        Integer employeurId
) {
}
