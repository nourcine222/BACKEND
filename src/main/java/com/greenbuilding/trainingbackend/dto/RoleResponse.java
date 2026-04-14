package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.RoleName;

public record RoleResponse(
        Integer id,
        RoleName name
) {
}
