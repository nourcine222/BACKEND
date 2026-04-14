package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.StatisticsResponse;

// Contract for dashboard and reporting metrics.
public interface StatisticsService {
    StatisticsResponse getOverview();
}
