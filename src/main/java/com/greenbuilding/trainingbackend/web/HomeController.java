package com.greenbuilding.trainingbackend.web;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Provides a simple landing response at the application root.
@RestController
public class HomeController {

    // Returns a small JSON payload instead of a Whitelabel 404 at "/".
    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "service", "training-backend",
                "status", "UP",
                "message", "Use /api/health, /swagger-ui.html, or /api/... endpoints"
        );
    }
}
