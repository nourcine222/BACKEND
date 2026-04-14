package com.greenbuilding.trainingbackend.dto;

// Outgoing payload that exposes domain data to the API client.
public record DomaineResponse(
        Integer id,
        String libelle
) {
}
