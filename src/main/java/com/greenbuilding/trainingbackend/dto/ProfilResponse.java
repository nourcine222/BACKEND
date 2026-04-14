package com.greenbuilding.trainingbackend.dto;

// Outgoing payload that exposes profile data to the API client.
public record ProfilResponse(
        Integer id,
        String libelle
) {
}
