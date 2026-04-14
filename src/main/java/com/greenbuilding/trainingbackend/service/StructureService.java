package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.StructureRequest;
import com.greenbuilding.trainingbackend.dto.StructureResponse;

import java.util.List;

// Contract for structure CRUD operations.
public interface StructureService {
    StructureResponse create(StructureRequest request);

    StructureResponse update(Integer id, StructureRequest request);

    StructureResponse getById(Integer id);

    List<StructureResponse> getAll();

    void delete(Integer id);
}
