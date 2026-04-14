package com.greenbuilding.trainingbackend.web;

import com.greenbuilding.trainingbackend.dto.StatisticsResponse;
import com.greenbuilding.trainingbackend.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Exposes backend summary metrics to responsable and admin users.
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    // Returns the dashboard overview used for monitoring by structure or domain.
    @GetMapping("/overview")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMINISTRATEUR')")
    public ResponseEntity<StatisticsResponse> overview() {
        return ResponseEntity.ok(statisticsService.getOverview());
    }
}
