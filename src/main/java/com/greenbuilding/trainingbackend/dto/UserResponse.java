package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.RoleName;

public record UserResponse(
        Integer id,
        String login,
        Integer roleId,
        RoleName roleName
) {
}
