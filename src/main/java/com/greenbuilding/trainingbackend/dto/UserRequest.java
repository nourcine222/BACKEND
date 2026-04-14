package com.greenbuilding.trainingbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank
        @Size(max = 80)
        String login,

        @NotBlank
        @Size(min = 6, max = 255)
        String password,

        @NotNull
        Integer roleId
) {
}
