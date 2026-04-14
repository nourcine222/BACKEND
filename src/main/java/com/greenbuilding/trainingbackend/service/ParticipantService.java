package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.ParticipantRequest;
import com.greenbuilding.trainingbackend.dto.ParticipantResponse;

import java.util.List;

// Contract for participant CRUD operations.
public interface ParticipantService {
    ParticipantResponse create(ParticipantRequest request);

    ParticipantResponse update(Integer id, ParticipantRequest request);

    ParticipantResponse getById(Integer id);

    List<ParticipantResponse> getAll();

    void delete(Integer id);
}
