package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.RoleName;
import jakarta.validation.constraints.NotNull;

public record RoleRequest(
        @NotNull
        RoleName name
) {
}
