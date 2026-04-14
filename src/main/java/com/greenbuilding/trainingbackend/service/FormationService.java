package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.FormationRequest;
import com.greenbuilding.trainingbackend.dto.FormationResponse;

import java.util.List;

public interface FormationService {
    FormationResponse create(FormationRequest request);

    FormationResponse update(Long id, FormationRequest request);

    FormationResponse getById(Long id);

    List<FormationResponse> getAll();

    void delete(Long id);
}
