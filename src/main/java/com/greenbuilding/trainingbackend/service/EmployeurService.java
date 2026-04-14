package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.EmployeurRequest;
import com.greenbuilding.trainingbackend.dto.EmployeurResponse;

import java.util.List;

// Contract for employer CRUD operations.
public interface EmployeurService {
    EmployeurResponse create(EmployeurRequest request);

    EmployeurResponse update(Integer id, EmployeurRequest request);

    EmployeurResponse getById(Integer id);

    List<EmployeurResponse> getAll();

    void delete(Integer id);
}
