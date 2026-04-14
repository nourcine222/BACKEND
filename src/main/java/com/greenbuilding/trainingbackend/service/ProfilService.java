package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.ProfilRequest;
import com.greenbuilding.trainingbackend.dto.ProfilResponse;

import java.util.List;

public interface ProfilService {
    ProfilResponse create(ProfilRequest request);

    ProfilResponse update(Integer id, ProfilRequest request);

    ProfilResponse getById(Integer id);

    List<ProfilResponse> getAll();

    void delete(Integer id);
}
