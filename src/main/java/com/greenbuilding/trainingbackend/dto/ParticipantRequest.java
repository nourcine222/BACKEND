package com.greenbuilding.trainingbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Incoming payload used to create or update a participant.
public record ParticipantRequest(
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
        Integer structureId,

        @NotNull
        Integer profilId
) {
}
