package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.DomaineRequest;
import com.greenbuilding.trainingbackend.dto.DomaineResponse;

import java.util.List;

// Contract for domain CRUD operations.
public interface DomaineService {
    DomaineResponse create(DomaineRequest request);

    DomaineResponse update(Integer id, DomaineRequest request);

    DomaineResponse getById(Integer id);

    List<DomaineResponse> getAll();

    void delete(Integer id);
}
