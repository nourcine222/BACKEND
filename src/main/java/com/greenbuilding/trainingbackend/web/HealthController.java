package com.greenbuilding.trainingbackend.web;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Lightweight endpoint used to confirm that the API is running.
@RestController
public class HealthController {

    // Returns a small JSON payload that can be used for health checks.
    @GetMapping("/api/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "training-backend",
                "timestamp", LocalDateTime.now().toString()
        );
    }
}
