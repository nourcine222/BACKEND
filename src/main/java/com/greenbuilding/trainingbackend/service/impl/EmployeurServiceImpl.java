package com.greenbuilding.trainingbackend.service.impl;

import com.greenbuilding.trainingbackend.dto.EmployeurRequest;
import com.greenbuilding.trainingbackend.dto.EmployeurResponse;
import com.greenbuilding.trainingbackend.entity.Employeur;
import com.greenbuilding.trainingbackend.exception.ResourceNotFoundException;
import com.greenbuilding.trainingbackend.repository.EmployeurRepository;
import com.greenbuilding.trainingbackend.service.EmployeurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Implements employer CRUD rules and DTO mapping.
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeurServiceImpl implements EmployeurService {

    private final EmployeurRepository employeurRepository;

    @Override
    public EmployeurResponse create(EmployeurRequest request) {
        String nomEmployeur = request.nomEmployeur().trim();

        employeurRepository.findByNomEmployeurIgnoreCase(nomEmployeur).ifPresent(employeur -> {
            throw new IllegalArgumentException("Employeur already exists");
        });

        Employeur saved = employeurRepository.save(
                Employeur.builder()
                        .nomEmployeur(nomEmployeur)
                        .build()
        );
        return toResponse(saved);
    }

    @Override
    public EmployeurResponse update(Integer id, EmployeurRequest request) {
        String nomEmployeur = request.nomEmployeur().trim();

        Employeur employeur = employeurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employeur not found with id " + id));

        employeurRepository.findByNomEmployeurIgnoreCase(nomEmployeur)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Employeur already exists");
                });

        employeur.setNomEmployeur(nomEmployeur);
        return toResponse(employeurRepository.save(employeur));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeurResponse getById(Integer id) {
        return employeurRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Employeur not found with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeurResponse> getAll() {
        return employeurRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        Employeur employeur = employeurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employeur not found with id " + id));
        employeurRepository.delete(employeur);
    }

    private EmployeurResponse toResponse(Employeur employeur) {
        return new EmployeurResponse(employeur.getId(), employeur.getNomEmployeur());
    }
}
