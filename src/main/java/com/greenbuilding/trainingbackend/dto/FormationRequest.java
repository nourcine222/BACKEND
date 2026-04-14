package com.greenbuilding.trainingbackend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record FormationRequest(
        @NotBlank
        @Size(max = 200)
        String titre,

        @NotNull
        @Min(1900)
        Integer annee,

        @NotNull
        @Min(1)
        Integer dureeJours,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal budget,

        @NotBlank
        @Size(max = 150)
        String lieu,

        @NotNull
        LocalDate dateFormation,

        @NotNull
        Integer domaineId,

        Long formateurId,

        List<Integer> participantIds
) {
}
