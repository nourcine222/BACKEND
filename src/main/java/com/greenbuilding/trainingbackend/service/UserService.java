package com.greenbuilding.trainingbackend.service;

import com.greenbuilding.trainingbackend.dto.UserRequest;
import com.greenbuilding.trainingbackend.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(UserRequest request);

    UserResponse update(Integer id, UserRequest request);

    UserResponse getById(Integer id);

    List<UserResponse> getAll();

    void delete(Integer id);
}
