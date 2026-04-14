package com.greenbuilding.trainingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DomaineRequest(
        @NotBlank
        @Size(max = 120)
        String libelle
) {
}
