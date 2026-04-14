package com.greenbuilding.trainingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeurRequest(
        @NotBlank
        @Size(max = 150)
        String nomEmployeur
) {
}
