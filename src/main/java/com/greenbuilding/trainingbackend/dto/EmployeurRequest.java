package com.greenbuilding.trainingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Incoming payload used to create or update an employer.
public record EmployeurRequest(
        @NotBlank
        @Size(max = 150)
        String nomEmployeur
) {
}
