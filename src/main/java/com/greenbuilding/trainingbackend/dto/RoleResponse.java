package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.RoleName;

// Outgoing payload that exposes role data to the API client.
public record RoleResponse(
        Integer id,
        RoleName name
) {
}
