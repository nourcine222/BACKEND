package com.greenbuilding.trainingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Incoming payload used to create or update a domain.
public record DomaineRequest(
        @NotBlank
        @Size(max = 120)
        String libelle
) {
}
