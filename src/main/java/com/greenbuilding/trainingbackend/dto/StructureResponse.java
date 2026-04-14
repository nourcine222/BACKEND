package com.greenbuilding.trainingbackend.dto;

// Outgoing payload that exposes structure data to the API client.
public record StructureResponse(
        Integer id,
        String libelle
) {
}
