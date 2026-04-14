package com.greenbuilding.trainingbackend.dto;

// Outgoing payload that exposes participant data to the API client.
public record ParticipantResponse(
        Integer id,
        String nom,
        String prenom,
        String email,
        String tel,
        Integer structureId,
        String structureLibelle,
        Integer profilId,
        String profilLibelle
) {
}
