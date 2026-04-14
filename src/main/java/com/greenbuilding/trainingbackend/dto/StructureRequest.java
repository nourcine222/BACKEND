package com.greenbuilding.trainingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StructureRequest(
        @NotBlank
        @Size(max = 150)
        String libelle
) {
}
