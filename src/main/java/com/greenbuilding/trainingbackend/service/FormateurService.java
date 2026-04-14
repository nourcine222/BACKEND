package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.FormateurRequest;
import com.greenbuilding.trainingbackend.dto.FormateurResponse;

import java.util.List;

public interface FormateurService {
    FormateurResponse create(FormateurRequest request);

    FormateurResponse update(Long id, FormateurRequest request);

    FormateurResponse getById(Long id);

    List<FormateurResponse> getAll();

    void delete(Long id);
}
