package com.greenbuilding.trainingbackend.dto;

import java.util.List;

// Outgoing payload that exposes the backend dashboard metrics.
public record StatisticsResponse(
        long totalUsers,
        long totalRoles,
        long totalDomaines,
        long totalProfils,
        long totalStructures,
        long totalEmployeurs,
        long totalFormateurs,
        long totalParticipants,
        long totalFormations,
        List<CategoryCount> formationsByDomain,
        List<CategoryCount> participantsByStructure,
        List<CategoryCount> participantsByProfile,
        List<CategoryCount> formateursByType
) {
    // Small generic count item used for charts and summaries.
    public record CategoryCount(
            String label,
            long count
    ) {
    }
}
