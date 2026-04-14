package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.RoleRequest;
import com.greenbuilding.trainingbackend.dto.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    RoleResponse update(Integer id, RoleRequest request);

    RoleResponse getById(Integer id);

    List<RoleResponse> getAll();

    void delete(Integer id);
}
