package com.greenbuilding.trainingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Incoming payload used to create or update a structure.
public record StructureRequest(
        @NotBlank
        @Size(max = 150)
        String libelle
) {
}
