package com.greenbuilding.trainingbackend.exception;

import java.time.LocalDateTime;
import java.util.Map;

// Standard error payload returned by the global exception handler.
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> validationErrors
) {
}
