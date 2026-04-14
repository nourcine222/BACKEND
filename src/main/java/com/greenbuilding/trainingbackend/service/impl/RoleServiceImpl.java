package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.RoleRequest;
import com.greenbuilding.trainingbackend.dto.RoleResponse;
import com.greenbuilding.trainingbackend.entity.Role;
import com.greenbuilding.trainingbackend.exception.ResourceNotFoundException;
import com.greenbuilding.trainingbackend.repository.RoleRepository;
import com.greenbuilding.trainingbackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Implements role CRUD rules and DTO mapping.
@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponse create(RoleRequest request) {
        // Avoid duplicate role names before inserting a new row.
        roleRepository.findByName(request.name()).ifPresent(role -> {
            throw new IllegalArgumentException("Role already exists");
        });

        Role saved = roleRepository.save(Role.builder().name(request.name()).build());
        return toResponse(saved);
    }

    @Override
    public RoleResponse update(Integer id, RoleRequest request) {
        // Load the role, validate the new name, then persist the change.
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

        roleRepository.findByName(request.name())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Role already exists");
                });

        role.setName(request.name());
        return toResponse(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getById(Integer id) {
        // Read one role and convert it to a response DTO.
        return roleRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAll() {
        // Return all roles as DTOs.
        return roleRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Integer id) {
        // Delete only after verifying that the role exists.
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
        roleRepository.delete(role);
    }

    private RoleResponse toResponse(Role role) {
        // Keep the response minimal and stable for clients.
        return new RoleResponse(role.getId(), role.getName());
    }
}
