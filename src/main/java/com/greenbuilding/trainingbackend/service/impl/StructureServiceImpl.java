package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.StructureRequest;
import com.greenbuilding.trainingbackend.dto.StructureResponse;
import com.greenbuilding.trainingbackend.entity.Structure;
import com.greenbuilding.trainingbackend.exception.ResourceNotFoundException;
import com.greenbuilding.trainingbackend.repository.StructureRepository;
import com.greenbuilding.trainingbackend.service.StructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Implements structure CRUD rules and DTO mapping.
@Service
@RequiredArgsConstructor
@Transactional
public class StructureServiceImpl implements StructureService {

    private final StructureRepository structureRepository;

    @Override
    public StructureResponse create(StructureRequest request) {
        String libelle = request.libelle().trim();

        structureRepository.findByLibelleIgnoreCase(libelle).ifPresent(structure -> {
            throw new IllegalArgumentException("Structure already exists");
        });

        Structure saved = structureRepository.save(
                Structure.builder()
                        .libelle(libelle)
                        .build()
        );
        return toResponse(saved);
    }

    @Override
    public StructureResponse update(Integer id, StructureRequest request) {
        String libelle = request.libelle().trim();

        Structure structure = structureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Structure not found with id " + id));

        structureRepository.findByLibelleIgnoreCase(libelle)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Structure already exists");
                });

        structure.setLibelle(libelle);
        return toResponse(structureRepository.save(structure));
    }

    @Override
    @Transactional(readOnly = true)
    public StructureResponse getById(Integer id) {
        return structureRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Structure not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StructureResponse> getAll() {
        return structureRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        Structure structure = structureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Structure not found with id " + id));
        structureRepository.delete(structure);
    }

    private StructureResponse toResponse(Structure structure) {
        return new StructureResponse(structure.getId(), structure.getLibelle());
    }
}
