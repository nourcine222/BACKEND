package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.RoleName;
import jakarta.validation.constraints.NotNull;

// Incoming payload used to create or update a role.
public record RoleRequest(
        @NotNull
        RoleName name
) {
}
