package com.greenbuilding.trainingbackend.dto;

import com.greenbuilding.trainingbackend.entity.RoleName;

// Outgoing payload that exposes user data to the API client.
public record UserResponse(
        Integer id,
        String login,
        Integer roleId,
        RoleName roleName
) {
}
