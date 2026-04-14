package com.greenbuilding.trainingbackend.dto;

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
