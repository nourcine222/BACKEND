package com.greenbuilding.trainingbackend.dto;

// Outgoing payload that exposes employer data to the API client.
public record EmployeurResponse(
        Integer id,
        String nomEmployeur
) {
}
